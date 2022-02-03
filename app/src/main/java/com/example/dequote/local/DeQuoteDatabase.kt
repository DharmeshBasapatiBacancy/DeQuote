package com.example.dequote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dequote.local.dao.QuotesDao
import com.example.dequote.local.dao.UserDao
import com.example.dequote.local.entites.FavQuotes
import com.example.dequote.local.entites.User

@Database(entities = [User::class, FavQuotes::class], version = 2)
abstract class DeQuoteDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun quotesDao(): QuotesDao

}