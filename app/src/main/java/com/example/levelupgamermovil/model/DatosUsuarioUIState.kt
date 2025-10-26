package com.example.levelupgamermovil.model

data class DatosUsuarioUIState(
    val nombre : String = "",
    val correo : String = "",
    val clave : String = "",
    val direccion : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : DatosUsuarioErrores = DatosUsuarioErrores()
)