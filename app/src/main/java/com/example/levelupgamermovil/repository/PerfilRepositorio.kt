package com.example.levelupgamermovil.repository

import android.net.Uri
import com.example.levelupgamermovil.model.PerfilUsuario

class PerfilRepositorio {
    private var perfilActual = PerfilUsuario(
        id = 1,
        nombre = "Usuario",
        imagenUri = null
    )

    fun getProfile(): PerfilUsuario = perfilActual

    fun updateImage(uri: Uri?) {
        perfilActual = perfilActual.copy(imagenUri = uri)
    }
}