package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.model.ProductoDao
import com.example.levelupgamermovil.R

class ProductRepository(private val productoDao: ProductoDao) {
    // 1. Obtiene la lista desde la base de datos local (Room)
    suspend fun getProductos(): List<Producto> = productoDao.getAll()

    // 2. Inserta un producto (útil para el Admin)
    suspend fun insertarProducto(producto: Producto) = productoDao.insert(producto)
    // 3. Guarda la lista
    suspend fun insertarLista(productos: List<Producto>) {
        productoDao.insertAll(productos)
    }
    suspend fun inicializarProductosSiVacio() {
    }
    suspend fun obtenerProductosDeApi(): List<Producto>? {
        return try {
            val respuesta = com.example.levelupgamermovil.network.RetrofitClient.webService.obtenerProductos()
            if (respuesta.isSuccessful) respuesta.body() else null
        } catch (e: Exception) {
            null
        }
    }
}
