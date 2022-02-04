package com.example.dequote.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.local.entites.Quote

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllQuotes(quotesList: List<Quote>)

    @Query("SELECT * FROM quote")
    fun getAllQuotes(): PagingSource<Int,Quote>

    @Query("DELETE FROM quote")
    suspend fun clearAll()

    @Query("UPDATE quote SET isFavorite = :isFavorite where _id = :quoteId")
    suspend fun updateFavoriteOrUnFavorite(isFavorite: Boolean, quoteId: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavQuote(favQuotes: FavQuotes)

    @Query("SELECT EXISTS(SELECT * from favQuotes where content = :content)")
    suspend fun checkIfQuoteExists(content: String): Boolean

    @Query("SELECT * FROM favQuotes")
    suspend fun getFavQuotes(): List<FavQuotes>

    @Delete
    suspend fun removeFavQuote(favQuotes: FavQuotes)
}