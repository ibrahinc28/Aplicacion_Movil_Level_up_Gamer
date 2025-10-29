package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.DatosUsuarioUIState

class UsuariosGuardados() {
    var usuarios = mutableSetOf<DatosUsuarioUIState>()

    fun agregarUsuario(usuarioNuevo : DatosUsuarioUIState) {
        usuarios.add(usuarioNuevo)
    }

    fun listarUsuarios(): String {
        var texto = String()

        texto = usuarios.toString()

        return texto
    }

    fun revisarUsuarioExisteLogin(usuarioCheckCorreo : String, usuarioCheckClave : String) : Boolean {
        for (x in usuarios) {
            if (x.correo == usuarioCheckCorreo && x.clave == usuarioCheckClave) {
                return true
            }
        }
        return false
    }
}