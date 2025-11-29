package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.UsuarioAPI
import com.example.levelupgamermovil.model.UsuarioDAO
import com.example.levelupgamermovil.network.RetrofitInstance
import retrofit2.Retrofit


class UsuarioRepository {
    suspend fun getUsuarios(): List<UsuarioAPI> {
        return RetrofitInstance.api.getUsuarios()
    }

    suspend fun insertarUsuario(usuario: UsuarioAPI) {
        RetrofitInstance.api.crearUsuario(usuario)
    }

}