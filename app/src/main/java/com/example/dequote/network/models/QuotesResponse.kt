package com.example.dequote.network.models

import com.example.dequote.local.entites.Quote

data class QuotesResponse(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Quote>,
    val totalCount: Int,
    val totalPages: Int
)