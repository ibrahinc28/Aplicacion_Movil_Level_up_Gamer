package com.example.levelupgamermovil.network

import com.example.levelupgamermovil.model.ConstantesUsuario
import com.example.levelupgamermovil.model.UsuarioAPI
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioAPIService {
    @GET("/api/usuarios")
    suspend fun getUsuarios(): List<UsuarioAPI>

    @POST("/api/usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioAPI): Response<UsuarioAPI>
}