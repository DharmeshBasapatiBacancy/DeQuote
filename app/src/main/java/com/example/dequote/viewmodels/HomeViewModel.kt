package com.example.dequote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.dequote.local.DeQuoteDatabase
import com.example.dequote.local.QuotesRemoteMediator
import com.example.dequote.local.dao.QuotesDao
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.local.entites.Quote
import com.example.dequote.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val quotesDao: QuotesDao,
    private val deQuoteDatabase: DeQuoteDatabase,
    private val apiService: ApiService
) :
    ViewModel() {

    @ExperimentalPagingApi
    fun getQuotesList(): Flow<PagingData<Quote>> =
        Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = { deQuoteDatabase.quotesDao().getAllQuotes() },
            remoteMediator = QuotesRemoteMediator(apiService, deQuoteDatabase)
        ).flow.cachedIn(viewModelScope)

    fun updateFavoriteQuoteInDB(isFavorite: Boolean, quoteId: String) {

        viewModelScope.launch {

            quotesDao.updateFavoriteOrUnFavorite(isFavorite, quoteId)

        }

    }

    private val _favQuotesList = MutableLiveData<List<FavQuotes>>()

    fun favQuotesList(): LiveData<List<FavQuotes>> = _favQuotesList

    fun getFavQuotesList() {
        viewModelScope.launch {
            _favQuotesList.postValue(
                quotesDao.getFavQuotes()
            )
        }
    }

    fun insertFavQuote(favQuotes: FavQuotes) {
        viewModelScope.launch {
            if (!quotesDao.checkIfQuoteExists(favQuotes.content)) {
                quotesDao.insertFavQuote(favQuotes)
            }
        }
    }

    fun removeFavQuote(favQuotes: FavQuotes) {
        viewModelScope.launch {
            quotesDao.updateFavoriteOrUnFavorite(false, favQuotes.favQuotesId)
            quotesDao.removeFavQuote(favQuotes)
            getFavQuotesList()
        }
    }

}