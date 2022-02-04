package com.example.dequote.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.dequote.local.entites.Quote
import com.example.dequote.local.entites.RemoteKeys
import com.example.dequote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class QuotesRemoteMediator(
    private val apiService: ApiService,
    private val deQuoteDatabase: DeQuoteDatabase
) : RemoteMediator<Int, Quote>() {

    private val STARTING_PAGE_INDEX = 1

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Quote>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = apiService.getQuotes(page)
            val endOfList = response.results.isEmpty()
            deQuoteDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    deQuoteDatabase.remoteKeysDao().clearAll()
                    deQuoteDatabase.quotesDao().clearAll()
                }
                val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfList) null else page + 1
                val keys = response.results.map {
                    RemoteKeys(it._id, prevKey, nextKey)
                }
                deQuoteDatabase.remoteKeysDao().insertRemote(keys)
                deQuoteDatabase.quotesDao().insertAllQuotes(response.results)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfList)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, Quote>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prevKey ?: MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?: MediatorResult.Success(
                    endOfPaginationReached = true
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, Quote>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .firstOrNull { it.data.isNotEmpty() }
                ?.data?.firstOrNull()
                ?.let { quote -> deQuoteDatabase.remoteKeysDao().getRemoteKeys(quote._id) }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, Quote>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.pages
                .lastOrNull { it.data.isNotEmpty() }
                ?.data?.lastOrNull()
                ?.let { quote -> deQuoteDatabase.remoteKeysDao().getRemoteKeys(quote._id) }
        }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, Quote>): RemoteKeys? {
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->
                state.closestItemToPosition(position)?._id?.let { repId ->
                    deQuoteDatabase.remoteKeysDao().getRemoteKeys(repId)
                }
            }
        }
    }

}