package com.example.levelupgamermovil.network

import com.example.levelupgamermovil.model.ConstantesUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {
    val api: UsuarioAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(ConstantesUsuario.api_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPIService::class.java)
    }
}