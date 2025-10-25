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

@Composable
fun UserVerify(navController : NavController) {

    Column (Modifier.padding(16.dp)) {
        Text("Usuario confirmado", style = MaterialTheme.typography.headlineSmall)

        Button(
            onClick = {navController.navigate("HomeScreen")},
            colors = ButtonDefaults.buttonColors(
                Color.Black,
                Color.White
            )
        )
        {
            Text("Aceptar")
        }
    }

}