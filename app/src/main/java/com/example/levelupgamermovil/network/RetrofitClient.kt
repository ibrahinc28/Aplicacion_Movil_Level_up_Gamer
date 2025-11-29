package com.example.levelupgamermovil.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // IMPORTANTE: Spring Boot suele correr en el puerto 8080.
    // 10.0.2.2 es el "localhost" del emulador de Android.
    private const val BASE_URL = "192.168.1.89:8080/"

    val webService: ProductoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductoApiService::class.java)
    }
}