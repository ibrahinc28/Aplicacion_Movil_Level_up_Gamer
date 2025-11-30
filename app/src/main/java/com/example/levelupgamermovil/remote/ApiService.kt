package com.example.levelupgamermovil.remote

import com.example.levelupgamermovil.model.CarritoEstado
import com.example.levelupgamermovil.model.ItemCarrito
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("cart")
    suspend fun getCart(): CarritoEstado

    @POST("cart/add")
    suspend fun addItem(@Body item: ItemCarrito): CarritoEstado

    @DELETE("cart/remove/{productId}")
    suspend fun removeItem(@Path("productId") id: String): CarritoEstado

    @POST("cart/clear")
    suspend fun clearCart(): CarritoEstado
}