package com.example.dequote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dequote.local.dao.QuotesDao
import com.example.dequote.local.dao.RemoteKeysDao
import com.example.dequote.local.dao.UserDao
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.local.entites.Quote
import com.example.dequote.local.entites.RemoteKeys
import com.example.dequote.local.entites.User
import com.example.dequote.utils.DataConverter

@Database(entities = [User::class, FavQuotes::class, Quote::class, RemoteKeys::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class DeQuoteDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun quotesDao(): QuotesDao
    abstract fun remoteKeysDao(): RemoteKeysDao

}