package com.example.dequote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
class HomeViewModel @Inject constructor(private val pagingRepository: PagingRepository) :
    ViewModel() {

    fun getQuotesList(): Flow<PagingData<Quote>> =
        pagingRepository.getQuotes().cachedIn(viewModelScope)

}