package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamermovil.model.UsuarioAPI
import com.example.levelupgamermovil.network.RetrofitInstance
import com.example.levelupgamermovil.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class UsuarioViewModel: ViewModel() {
    val repositorio = UsuarioRepository()

    val _userList = MutableStateFlow<List<UsuarioAPI>>(emptyList())

    val userList: StateFlow<List<UsuarioAPI>> = _userList

    init {
        fetchUsers()
    }

    open fun fetchUsers() {
        viewModelScope.launch {
            try {
                _userList.value = repositorio.getUsuarios()
            } catch (e: Exception) {
                println("Hubo un error: ${e.localizedMessage}")
            }
        }
    }
}