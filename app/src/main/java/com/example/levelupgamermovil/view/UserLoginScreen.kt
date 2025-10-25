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
import com.example.levelupgamermovil.viewmodel.RegistroViewModel
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.font.FontWeight
import com.example.levelupgamermovil.repository.UsuariosGuardados

@Composable
fun userLoginScreen(navController : NavController, viewModel: RegistroViewModel) {
    val estado by viewModel.estado.collectAsState()
    val usuarios = UsuariosGuardados()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        Arrangement.spacedBy(12.dp),
    ) {
        Text(text = "Iniciar sesión", style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )

        OutlinedTextField(
            value = estado.correo,
            onValueChange = viewModel::onCorreoChange,
            label = { Text("Correo") },
            isError = estado.errores.correo != null,
            supportingText = {
            estado.errores.correo?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
        }
    },
        modifier = Modifier.fillMaxWidth()
)
        OutlinedTextField(
            value = estado.clave,
            onValueChange = viewModel::onClaveChange,
            label = { Text("Clave") },
            visualTransformation = PasswordVisualTransformation(),
            isError = estado.errores.clave != null,
            supportingText = {
                estado.errores.clave?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
            },
        modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (usuarios.revisarUsuarioExisteLogin(estado.correo, estado.clave)) {
                    navController.navigate("UserVerify")
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