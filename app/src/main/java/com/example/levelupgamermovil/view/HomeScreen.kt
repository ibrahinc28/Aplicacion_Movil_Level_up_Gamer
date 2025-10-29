package com.example.levelupgamermovil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelupgamermovil.R
import com.example.levelupgamermovil.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Level Up Gamer") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(24.dp))

            Text(text = "Menú principal", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

            Button(
                onClick = { navController.navigate(NavRoutes.PRODUCTOS) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text("Productos")
            }

            Button(
                onClick = { navController.navigate(NavRoutes.CARRITO) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
            ) {
                Text("Carrito")
            }

            Button(
                onClick = { navController.navigate(NavRoutes.RESUMEN) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFFF57C00))
            ) {
                Text("Resumen")
            }

            Button(
                onClick = { navController.navigate(NavRoutes.LOGIN) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF8E24AA))
            ) {
                Text("Usuarios")
            }
        }
    }
}
