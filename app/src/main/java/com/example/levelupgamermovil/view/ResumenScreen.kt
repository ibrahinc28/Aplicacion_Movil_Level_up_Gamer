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
        Text("Éxito", style = MaterialTheme.typography.headlineMedium)
        Text("El usuario se ha registrado de manera exitosa", style = MaterialTheme.typography.headlineSmall)

        guardarUsuario.agregarUsuario(DatosUsuarioUIState(estado.nombre, estado.correo, estado.clave, estado.direccion, estado.aceptaTerminos))

        Button(
            onClick = {navController.navigate("HomeScreen")},
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