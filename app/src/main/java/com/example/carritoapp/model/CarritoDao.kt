package com.example.carritoapp.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {

    @Query("SELECT * FROM carrito_items")
    fun obtenerItemsCarrito(): Flow<List<CarritoItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(item: CarritoItemEntity)

    @Update
    suspend fun actualizar(item: CarritoItemEntity)

    @Query("DELETE FROM carrito_items WHERE codigoProducto = :codigo")
    suspend fun eliminarPorCodigo(codigo: String)

    @Query("DELETE FROM carrito_items")
    suspend fun vaciar()
}