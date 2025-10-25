package com.example.carritoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.carritoapp.model.ThemeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel (application: Application): AndroidViewModel(application){
    private val themeDataStore = ThemeDataStore(application)
    val esOscuro: StateFlow<Boolean> = themeDataStore.obtenerModoOscuro()
        .map {valorLeido ->
            valorLeido
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )
    fun alternarTema(){
        viewModelScope.launch {
            val nuevoValor = !esOscuro.value
            themeDataStore.guardarModoOscuro(nuevoValor)
        }
    }
    companion object{
        fun Factory(application: Application): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ThemeViewModel(application)
            }
        }
    }
}