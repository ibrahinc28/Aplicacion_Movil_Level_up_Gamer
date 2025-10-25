package com.example.levelupgamermovil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamermovil.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun homeScreen(navController : NavController) {


    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Placeholder") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                "Logo App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit

            )

            Text(text = "Texto de ejemplo",
                fontWeight = FontWeight.Bold
            )
            Button(onClick = {navController.navigate("UserSignupScreen")},
                colors = ButtonDefaults.buttonColors(
                    Color.Green,
                    Color.White
                )
            ) {
                Text("Registrar usuario")
            }
            Button(onClick = {navController.navigate("UserLoginScreen")},
                colors = ButtonDefaults.buttonColors(
                    Color.Red,
                    Color.White
                )
            ) {
                Text("Probar login")
            }
        }

    }
}

