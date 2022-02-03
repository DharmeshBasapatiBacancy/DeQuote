package com.example.dequote.local.dao

import androidx.room.*
import com.example.dequote.local.entites.FavQuotes

@Dao
interface QuotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavQuote(favQuotes: FavQuotes)

    @Query("SELECT EXISTS(SELECT * from favQuotes where content = :content)")
    suspend fun checkIfQuoteExists(content: String): Boolean

    @Query("SELECT * FROM favQuotes")
    suspend fun getFavQuotes(): List<FavQuotes>

    @Delete
    suspend fun removeFavQuote(favQuotes: FavQuotes)
}