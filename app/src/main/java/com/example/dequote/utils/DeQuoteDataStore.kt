package com.example.dequote.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DeQuoteDataStore(@ApplicationContext context: Context) {

    private val appContext = context.applicationContext

    companion object {

        private const val DATASTORE_NAME = "deQuoteDataStore"

        private val EMAIL_KEY = stringPreferencesKey("email")

        private val PASSWORD_KEY = stringPreferencesKey("password")

        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

        private val Context.deQuoteDataStore by preferencesDataStore(name = DATASTORE_NAME)

    }

    suspend fun setEmail(email: String) {
        appContext.deQuoteDataStore.edit { prefs ->
            prefs[EMAIL_KEY] = email
        }
    }

    suspend fun setPassword(password: String) {
        appContext.deQuoteDataStore.edit { prefs ->
            prefs[PASSWORD_KEY] = password
        }
    }

    suspend fun setIsLoggedIn(isLoggedIn: Boolean) {
        appContext.deQuoteDataStore.edit { prefs ->
            prefs[IS_LOGGED_IN_KEY] = isLoggedIn
        }
    }

    val email: Flow<String>
        get() = appContext.deQuoteDataStore.data.map {
            it[EMAIL_KEY] ?: ""
        }

    val password: Flow<String>
        get() = appContext.deQuoteDataStore.data.map {
            it[PASSWORD_KEY] ?: ""
        }

    val isLoggedIn: Flow<Boolean>
        get() = appContext.deQuoteDataStore.data.map {
            it[IS_LOGGED_IN_KEY] ?: false
        }

    suspend fun clear() {
        appContext.deQuoteDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}