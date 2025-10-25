package com.example.carritoapp.repository

import com.example.carritoapp.model.CarritoDao
import com.example.carritoapp.model.CarritoItemEntity
import kotlinx.coroutines.flow.Flow

class CarritoRepository(private val dao: CarritoDao){

    fun obtenerItemsCarrito(): Flow<List<CarritoItemEntity>> = dao.obtenerItemsCarrito()

    suspend fun insertar(item: CarritoItemEntity) = dao.insertar(item)
    suspend fun actualizar(item: CarritoItemEntity) = dao.actualizar(item)
    suspend fun eliminarPorCodigo(codigo: String) = dao.eliminarPorCodigo(codigo)
    suspend fun vaciar() = dao.vaciar()
}