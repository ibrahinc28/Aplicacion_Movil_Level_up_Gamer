package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import com.example.levelupgamermovil.model.LoginErrores
import com.example.levelupgamermovil.model.UsuarioLogin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {
    private val _estado = MutableStateFlow(UsuarioLogin())

    val estado: StateFlow<UsuarioLogin> = _estado

    fun onCorreoChange (valor : String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }
    fun onClaveChange (valor : String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = LoginErrores(
            correo = if (!estadoActual.correo.contains("@")) "El correo no es válido" else null,
            clave = if (estadoActual.clave.length < 8) "La contraseña debe tener al menos 8 caracteres" else null,
        )
        val hayErrores = listOfNotNull(
            errores.correo,
            errores.clave
        ).isNotEmpty()
        _estado.update { it.copy(errores = errores) }

        return !hayErrores

    }
}
