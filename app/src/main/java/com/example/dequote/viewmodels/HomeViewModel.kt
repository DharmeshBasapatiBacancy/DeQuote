package com.example.dequote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.dequote.local.dao.QuotesDao
import com.example.dequote.local.dao.UserDao
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.network.models.Quote
import com.example.dequote.network.models.QuotesResponse
import com.example.dequote.paging.PagingRepository
import com.example.dequote.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pagingRepository: PagingRepository,
    private val quotesDao: QuotesDao
) :
    ViewModel() {

    fun getQuotesList(): Flow<PagingData<Quote>> =
        pagingRepository.getQuotes().cachedIn(viewModelScope)

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

            quotesDao.removeFavQuote(favQuotes)
            getFavQuotesList()
        }

    }

}