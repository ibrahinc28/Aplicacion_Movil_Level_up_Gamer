package com.example.levelupgamermovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupgamermovil.navigation.NavRoutes
import com.example.levelupgamermovil.view.LoginScreen
import com.example.levelupgamermovil.view.UserSignupScreen
import com.example.levelupgamermovil.view.HomeScreen
import com.example.levelupgamermovil.view.ProductListScreen
import com.example.levelupgamermovil.view.CarritoScreen
import com.example.levelupgamermovil.view.ResumenScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavRoutes.LOGIN
            ) {
                composable(NavRoutes.LOGIN) { LoginScreen(navController) }
                composable(NavRoutes.SIGNUP) { UserSignupScreen(navController) }
                composable(NavRoutes.HOME) { HomeScreen(navController) }
                composable(NavRoutes.PRODUCTOS) { ProductListScreen(navController) }
                composable(NavRoutes.CARRITO) { CarritoScreen(navController) }
                composable(NavRoutes.RESUMEN) { ResumenScreen(navController) }
            }
        }
    }
}
