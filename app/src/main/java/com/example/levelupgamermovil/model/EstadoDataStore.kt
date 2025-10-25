package com.example.levelupgamermovil.model

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferences-usuario")

class EstadoDataStore(private val context: Context) {
    private val ESTADO_ACTIVADO = booleanPreferencesKey("modo-activado")

    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ESTADO_ACTIVADO] = valor
        }
    }

    fun obtenerEstado(): Flow<Boolean?> {
        return context.dataStore.data.map { preferences ->
            preferences[ESTADO_ACTIVADO]
        }
    }
}