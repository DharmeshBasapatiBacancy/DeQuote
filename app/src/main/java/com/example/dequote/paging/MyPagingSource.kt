package com.example.dequote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.dequote.network.ApiService
import com.example.dequote.network.models.QuoteApi

private const val INITIAL_PAGE = 1

class MyPagingSource(
    private val api: ApiService,
)
    //: PagingSource<Int, QuoteApi>()
{

//    override fun getRefreshKey(state: PagingState<Int, QuoteApi>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteApi> {
//        return try {
////            val page = params.key ?: INITIAL_PAGE
////            val response = api.getQuotes(page)
////            LoadResult.Page(
////                data = response.results,
////                prevKey = if (page == INITIAL_PAGE) null else page - 1,
////                nextKey = if (response.results.isEmpty()) null else page + 1
////            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
}