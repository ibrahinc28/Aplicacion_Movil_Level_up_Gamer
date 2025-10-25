package com.example.levelupgamermovil.model

import com.example.levelupgamermovil.model.LoginErrores

data class UsuarioLogin(
    val correo : String = "",
    val clave : String = "",
    val errores : LoginErrores = LoginErrores()
)