package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.model.ProductoDao

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
            if (respuesta.isSuccessful) respuesta.body() else {
                android.util.Log.e("ERROR_API", "Error del servidor: ${respuesta.code()}")
                android.util.Log.e("ERROR_API", "Resultado: ${respuesta.raw()}")

                null
            }
        } catch (e: Exception) {
            android.util.Log.e("ERROR_API", "Fallo critico: ${e.message}")
            e.printStackTrace()
            null
        }
    }
}
