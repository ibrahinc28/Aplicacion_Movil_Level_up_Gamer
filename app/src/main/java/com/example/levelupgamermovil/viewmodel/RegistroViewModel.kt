package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamermovil.model.DatosUsuarioErrores
import com.example.levelupgamermovil.model.DatosUsuarioUIState
import com.example.levelupgamermovil.model.UsuarioAPI
import com.example.levelupgamermovil.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel : ViewModel() {
    private val _estado = MutableStateFlow(DatosUsuarioUIState())

    val estado: StateFlow<DatosUsuarioUIState> = _estado

    fun onNombreChange (valor : String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onApellidoPatChange (valor : String) {
        _estado.update { it.copy(apellidopat = valor, errores = it.errores.copy(apellidopat = null)) }
    }

    fun onApellidoMatChange (valor : String) {
        _estado.update { it.copy(apellidomat = valor, errores = it.errores.copy(apellidomat = null)) }
    }

    fun onCorreoChange (valor : String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange (valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onAceptarTerminosChange (valor : Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = DatosUsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Debe ingresar su nombre" else null,
            apellidopat = if (estadoActual.apellidopat.isBlank()) "Debe ingresar su apellido paterno" else null,
            apellidomat = if (estadoActual.apellidomat.isBlank()) "Debe ingresar su apellido materno" else null,
            clave = if (estadoActual.clave.length < 8) "Su contraseña debe contener al menos 8 caracteres" else null,
            correo = if (!estadoActual.correo.contains("@")) "El correo no es válido" else null,
        )
        val hayErrores = listOfNotNull(
            errores.nombre,
            errores.apellidopat,
            errores.apellidomat,
            errores.clave,
            errores.correo,
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores

    }

    fun agregarUsuario(usuario: UsuarioAPI) {
        viewModelScope.launch {
            try {
                val resp = RetrofitInstance.api.crearUsuario(usuario)
            } catch (e: Exception) {
                println("ERROR AL AGREGAR USUARIO: ${e.message}")
            }
        }
    }
}