package com.example.levelupgamermovil.remote

import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.model.Cart
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("cart")
    suspend fun getCart(): Cart

    @POST("cart/add")
    suspend fun addItem(@Body item: CartItem): Cart

    @DELETE("cart/remove/{productId}")
    suspend fun removeItem(@Path("productId") id: String): Cart

    @POST("cart/clear")
    suspend fun clearCart(): Cart
}