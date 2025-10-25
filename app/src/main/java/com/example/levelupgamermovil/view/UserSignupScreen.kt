package com.example.levelupgamermovil.view

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.NavController

@Composable
fun userSignupScreen(navController : NavController) {
    Button(
        onClick = {navController.popBackStack()}
    )
    {
        Text("Volver")
    }
}