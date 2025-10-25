package com.example.levelupgamermovil.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import com.example.levelupgamermovil.repository.UsuariosGuardados
import com.example.levelupgamermovil.viewmodel.LoginViewModel

@Composable
fun LoginScreen(navController : NavController, viewModel: LoginViewModel) {
    val estado2 by viewModel.estado.collectAsState()
    val usuario = UsuariosGuardados()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Crear usuario", style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        OutlinedTextField(
            value = estado2.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Correo") },
            isError = estado2.errores.correo != null,
            supportingText = {
                estado2.errores.correo?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = estado2.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Clave") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado2.errores.clave != null,
            supportingText = {
                estado2.errores.clave?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (viewModel.validarFormulario()) {
                    navController.navigate("HomeScreen")
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar sesión")
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