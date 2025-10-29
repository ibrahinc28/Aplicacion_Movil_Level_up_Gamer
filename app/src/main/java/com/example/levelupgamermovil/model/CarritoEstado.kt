package com.example.levelupgamermovil.model

data class CarritoEstado (
    val items: List<ItemCarrito> = emptyList(),
    val subtotal: Double = 0.0,
    val costoEnvio: Double = 5000.0,
    val total: Double = 0.0,
    val totalArticulos: Int = 0,
    val mensajeConfirmado: String? = null,
    val compraFinalizada: Boolean = false
)