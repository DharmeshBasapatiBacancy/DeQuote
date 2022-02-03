package com.example.dequote.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.dequote.network.ApiService
import javax.inject.Inject

class PagingRepository @Inject constructor(private val apiService: ApiService) {

    fun getQuotes() = Pager(
        pagingSourceFactory = { MyPagingSource(apiService) },
        config = PagingConfig(
            pageSize = 20
        )
    ).flow

}