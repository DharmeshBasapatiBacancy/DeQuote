package com.example.dequote.di

import android.content.Context
import androidx.room.Room
import com.example.dequote.local.DeQuoteDatabase
import com.example.dequote.local.dao.QuotesDao
import com.example.dequote.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideUserDao(deQuoteDatabase: DeQuoteDatabase): UserDao {
        return deQuoteDatabase.userDao()
    }

    @Provides
    fun provideQuotesDao(deQuoteDatabase: DeQuoteDatabase): QuotesDao {
        return deQuoteDatabase.quotesDao()
    }

    @Provides
    @Singleton
    fun provideDbInstance(@ApplicationContext context: Context): DeQuoteDatabase {
        return Room.databaseBuilder(
            context,
            DeQuoteDatabase::class.java,
            "DeQuoteDatabase"
        ).build()
    }

}