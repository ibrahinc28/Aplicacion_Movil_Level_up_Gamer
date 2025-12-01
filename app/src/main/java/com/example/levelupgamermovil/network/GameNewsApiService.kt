package com.example.levelupgamermovil.network

import com.example.levelupgamermovil.model.GameNews
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface GameNewsApiService {
    @GET("api/games?sort-by=popularity") // Trae los populares
    suspend fun getFreeGames(): Response<List<GameNews>>
}

// Cliente Singleton específico para esta API externa
object ExternalRetrofitClient {
    private const val BASE_URL = "https://www.freetogame.com/"

    val service: GameNewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GameNewsApiService::class.java)
    }
}