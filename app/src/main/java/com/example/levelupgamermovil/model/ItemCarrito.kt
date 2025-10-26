package com.example.levelupgamermovil.model

data class ItemCarrito (val codigoProducto: String,
                        val nombre: String,
                        val precio: Double,
                        var cantidad: Int)