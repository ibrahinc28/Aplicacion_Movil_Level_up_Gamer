package com.example.levelupgamermovil.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.DrawableRes

@Entity(tableName= "productos")
data class Producto(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val imageRes: Int
)
