package com.example.dequote.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeQuoteDataStore(private val context: Context) {

    companion object {

        private const val DATASTORE_NAME = "deQuoteDataStore"

        private val EMAIL_KEY = stringPreferencesKey("email")

        private val PASSWORD_KEY = stringPreferencesKey("password")

        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

        private val Context.deQuoteDataStore by preferencesDataStore(name = DATASTORE_NAME)

    }

    suspend fun setEmail(email: String){
        context.deQuoteDataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun setPassword(password: String){
        context.deQuoteDataStore.edit { prefs ->
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean){
        context.deQuoteDataStore.edit { prefs ->
            prefs[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    val email: Flow<String>
        get() = context.deQuoteDataStore.data.map {
            it[EMAIL_KEY] ?: ""
        }

    val password: Flow<String>
        get() = context.deQuoteDataStore.data.map {
            it[PASSWORD_KEY] ?: ""
        }

    val isLoggedIn: Flow<Boolean>
        get() = context.deQuoteDataStore.data.map {
            it[IS_LOGGED_IN_KEY] ?: false
        }
}