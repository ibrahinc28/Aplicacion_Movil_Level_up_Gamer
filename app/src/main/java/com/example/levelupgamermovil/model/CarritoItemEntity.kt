package com.example.levelupgamermovil.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "carrito_items")
data class CarritoItemEntity (

    @PrimaryKey val codigoProducto: String,
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)