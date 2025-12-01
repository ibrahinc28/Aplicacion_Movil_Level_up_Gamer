package com.example.levelupgamermovil.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamermovil.viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsuarioAPIScreen(navController : NavController, viewModel: UsuarioViewModel) {

    val usuarios = viewModel.userList.collectAsState().value


    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Usuarios Registrados") }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            LazyColumn (modifier = Modifier
                .fillMaxSize()
                .padding(16.dp))
            {
                items(usuarios)
                {usuario ->
                Text(text = "USUARIO NÚMERO ${usuario.id}")
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Nombre: ${usuario.nombre}")
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = (
                        if (usuario.snombre == null) {
                            "Segundo Nombre: (NINGUNO)"
                        } else {
                            "Segundo Nombre: ${usuario.snombre}"
                        }
                        ))
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Apellido Paterno: ${usuario.apellidopat}")
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Apellido Materno: ${usuario.apellidomat}")
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Contraseña: ${usuario.contrasena}")
                    Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Correo: ${usuario.correo}")
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
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
}
