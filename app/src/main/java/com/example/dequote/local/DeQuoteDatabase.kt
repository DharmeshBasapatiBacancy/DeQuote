package com.example.dequote.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dequote.local.dao.UserDao
import com.example.dequote.local.entites.User

@Database(entities = [User::class], version = 1)
abstract class DeQuoteDatabase: RoomDatabase() {

    abstract fun userDao() : UserDao

}