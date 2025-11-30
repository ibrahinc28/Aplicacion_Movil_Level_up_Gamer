package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.CarritoDao
import com.example.levelupgamermovil.model.CarritoItemEntity
import com.example.levelupgamermovil.model.CarritoEstado
import com.example.levelupgamermovil.model.ItemCarrito
import com.example.levelupgamermovil.remote.ApiService
import kotlinx.coroutines.flow.Flow

class CarritoRepository(
    private val dao: CarritoDao,
    private val apiService: ApiService
){
    fun obtenerItemsCarrito(): Flow<List<CarritoItemEntity>> = dao.obtenerItemsCarrito()
    suspend fun insertar(item: CarritoItemEntity) = dao.insertar(item)
    suspend fun actualizar(item: CarritoItemEntity) = dao.actualizar(item)
    suspend fun eliminarPorCodigo(codigo: String) = dao.eliminarPorCodigo(codigo)
    suspend fun vaciarLocal() = dao.vaciar()

    suspend fun obtenerCarritoRemoto(): CarritoEstado {
        return apiService.getCart()
    }

    suspend fun agregarItemRemoto(item: ItemCarrito): CarritoEstado {
        return apiService.addItem(item)
    }

    suspend fun eliminarItemRemoto(codigo: String): CarritoEstado {
        return apiService.removeItem(codigo)
    }

    suspend fun finalizarCompraRemota(items: List<ItemCarrito>) {
        try {
            apiService.clearCart()
            dao.vaciar()
        } catch (e: Exception) {
            throw e
        }
    }
}