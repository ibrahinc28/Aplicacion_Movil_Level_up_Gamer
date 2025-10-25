package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.DatosUsuarioUIState

class UsuariosGuardados {
    val usuarios = mutableSetOf<DatosUsuarioUIState>()

    fun agregarUsuario(usuarioNuevo : DatosUsuarioUIState) {
        usuarios.add(usuarioNuevo)
    }

    fun listarUsuarios(): String {
        var texto = String()

        texto = usuarios.toString()

        return texto
    }
}