package com.example.dequote.di

import android.content.Context
import com.example.dequote.network.ApiService
import com.example.dequote.paging.PagingRepository
import com.example.dequote.utils.DeQuoteDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DeQuoteDataStore {
        return DeQuoteDataStore(context)
    }

    @Singleton
    @Provides
    fun providePagingRepo(apiService: ApiService): PagingRepository {
        return PagingRepository(apiService)
    }

}