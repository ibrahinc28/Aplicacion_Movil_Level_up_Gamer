package com.example.levelupgamermovil.network

import com.example.levelupgamermovil.model.Producto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductoApiService {


    @GET("api/productos")
    suspend fun obtenerProductos(): Response<List<Producto>>


    @GET("api/productos/destacados")
    suspend fun obtenerDestacados(): Response<List<Producto>>


    @GET("api/productos/{id}")
    suspend fun obtenerProductoPorId(@Path("id") id: Long): Response<Producto>


    @POST("api/productos")
    suspend fun crearProducto(@Body producto: Producto): Response<Producto>
}