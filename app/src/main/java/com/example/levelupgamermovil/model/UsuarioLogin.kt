package com.example.levelupgamermovil.model

data class UsuarioLogin(
    val correo : String = "",
    val clave : String = "",
    val errores : LoginErrores = LoginErrores()
)