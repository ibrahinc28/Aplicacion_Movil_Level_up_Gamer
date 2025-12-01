package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.UsuarioAPI
import com.example.levelupgamermovil.network.RetrofitClient


open class UsuarioRepository {
    open suspend fun getUsuarios(): List<UsuarioAPI> {
        return RetrofitClient.apiUser.getUsuarios()
    }

    suspend fun insertarUsuario(usuario: UsuarioAPI) {
        RetrofitClient.apiUser.crearUsuario(usuario)
    }

}