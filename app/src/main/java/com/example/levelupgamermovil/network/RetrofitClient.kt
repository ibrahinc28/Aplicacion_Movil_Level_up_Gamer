package com.example.levelupgamermovil.network

import com.example.levelupgamermovil.model.ConstantesUsuario
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // IMPORTANTE: Spring Boot suele correr en el puerto 8080.
    // 10.0.2.2 es el "localhost" del emulador de Android.
    private const val BASE_URL = "http://10.0.2.2:8080/api/"

    val webService: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }

    val apiUser: UsuarioAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(ConstantesUsuario.api_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioAPIService::class.java)
    }
}