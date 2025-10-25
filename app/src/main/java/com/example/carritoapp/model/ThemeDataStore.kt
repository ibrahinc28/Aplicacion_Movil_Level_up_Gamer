package com.example.carritoapp.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("theme_settings")

class ThemeDataStore (private val context: Context){
    private companion object{
        val IS_DARK_MODE_KEY = booleanPreferencesKey("is_dark_mode")
    }

    fun obtenerModoOscuro(): Flow<Boolean> {
        return context.dataStore.data
            .map { preferences ->
                preferences[IS_DARK_MODE_KEY] ?: false
            }
    }

    suspend fun guardarModoOscuro(isDark: Boolean){
        context.dataStore.edit {preferences ->
            preferences[IS_DARK_MODE_KEY] = isDark
        }
    }
}