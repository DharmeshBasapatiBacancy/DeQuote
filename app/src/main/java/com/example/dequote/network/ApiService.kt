package com.example.dequote.network

import com.example.dequote.network.models.QuotesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("quotes")
    suspend fun getQuotes(
        @Query("page") page: Int,
    ): QuotesResponse

}