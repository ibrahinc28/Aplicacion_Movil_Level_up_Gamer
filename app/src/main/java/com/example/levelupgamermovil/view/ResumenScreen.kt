package com.example.levelupgamermovil.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.levelupgamermovil.viewmodel.RegistroViewModel
import androidx.navigation.compose.NavHost
import com.example.levelupgamermovil.model.DatosUsuarioUIState
import com.example.levelupgamermovil.repository.UsuariosGuardados

@Composable
fun ResumenScreen(navController : NavController, viewModel: RegistroViewModel) {
    val estado by viewModel.estado.collectAsState()
    val guardarUsuario = UsuariosGuardados()

    Column (Modifier.padding(16.dp)) {
        Text("Resúmen del registro", style = MaterialTheme.typography.headlineMedium)
        Text("Nombre: ${estado.nombre}")
        Text("Correo: ${estado.correo}")
        Text("Dirección: ${estado.direccion}")
        Text("Contraseña: ${"*".repeat(estado.clave.length)}")
        Text("¿Términos aceptados? ${if (estado.aceptaTerminos) "Aceptados" else "Declinados"}")

        guardarUsuario.agregarUsuario(DatosUsuarioUIState(estado.nombre, estado.correo, estado.direccion, estado.clave, estado.aceptaTerminos))

        Text("Los usuarios guardados actualmente son: ${guardarUsuario.listarUsuarios()}"
        ) // Este texto es con proposito debug, quitar o comentar al finalizar esta seccion

        Button(
            onClick = {navController.popBackStack()},
            colors = ButtonDefaults.buttonColors(
                Color.Black,
                Color.White
            )
        )
        {
            Text("Volver")
        }
    }

}