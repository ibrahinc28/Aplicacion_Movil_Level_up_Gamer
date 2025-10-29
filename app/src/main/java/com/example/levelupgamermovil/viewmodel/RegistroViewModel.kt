package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamermovil.model.DatosUsuarioErrores
import com.example.levelupgamermovil.model.DatosUsuarioUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegistroViewModel : ViewModel() {
    private val _estado = MutableStateFlow(DatosUsuarioUIState())

    val estado: StateFlow<DatosUsuarioUIState> = _estado

    fun onNombreChange (valor : String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange (valor : String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange (valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange (valor : String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onAceptarTerminosChange (valor : Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = DatosUsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Debe ingresar su nombre" else null,
            correo = if (!estadoActual.correo.contains("@")) "El correo no es válido" else null,
            clave = if (estadoActual.clave.length < 8) "Su contraseña debe contener al menos 8 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Debe ingresar su dirección" else null
        )
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.correo,
            errores.clave,
            errores.direccion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores

    }
}