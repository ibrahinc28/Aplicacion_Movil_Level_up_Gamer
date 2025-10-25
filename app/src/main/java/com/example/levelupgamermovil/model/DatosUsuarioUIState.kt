package com.example.levelupgamermovil.model
import com.example.levelupgamermovil.model.DatosUsuarioErrores

data class DatosUsuarioUIState(
    val nombre : String = "",
    val correo : String = "",
    val clave : String = "",
    val direccion : String = "",
    val aceptaTerminos : Boolean = false,
    val errores : DatosUsuarioErrores = DatosUsuarioErrores()
)