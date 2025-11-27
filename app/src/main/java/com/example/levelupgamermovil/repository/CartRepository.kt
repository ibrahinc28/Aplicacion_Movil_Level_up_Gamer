package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.remote.ApiService
import com.example.levelupgamermovil.remote.RetrofitInstance

open class CartRepository (private val api: ApiService = RetrofitInstance.api){

    open suspend fun getCart() = api.getCart()

    suspend fun addItem(item: CartItem) = api.addItem(item)

    suspend fun removeItem(id: String) = api.removeItem(id)

    suspend fun clearCart() = api.clearCart()
}