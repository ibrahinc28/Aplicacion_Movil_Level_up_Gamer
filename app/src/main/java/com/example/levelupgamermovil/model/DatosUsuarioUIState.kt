package com.example.levelupgamermovil.model

data class DatosUsuarioUIState(
    val nombre : String = "",
    val snombre : String = "",
    val apellidopat : String = "",
    val apellidomat : String = "",
    val correo : String = "",
    val clave : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : DatosUsuarioErrores = DatosUsuarioErrores()
)