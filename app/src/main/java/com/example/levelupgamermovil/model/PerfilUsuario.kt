package com.example.levelupgamermovil.model

import android.net.Uri

data class PerfilUsuario(
    val id: Int,
    val nombre: String,
    val imagenUri: Uri? = null
)
