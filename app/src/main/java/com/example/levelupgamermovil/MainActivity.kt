package com.example.levelupgamermovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupgamermovil.ui.theme.LevelUpGamerMovilTheme
import com.example.levelupgamermovil.view.LoginScreen
import com.example.levelupgamermovil.view.ResumenScreen
import com.example.levelupgamermovil.view.homeScreen
import com.example.levelupgamermovil.view.userSignupScreen
import com.example.levelupgamermovil.viewmodel.LoginViewModel
import com.example.levelupgamermovil.viewmodel.RegistroViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val regViewModel : RegistroViewModel = viewModel()
            val loginViewModel : LoginViewModel = viewModel()

            NavHost(navController = navController, startDestination = "HomeScreen") {
                composable("HomeScreen") {
                    homeScreen(navController)
                }
                composable("UserSignupScreen") {
                    userSignupScreen(
                        navController, regViewModel
                    )
                }
                composable("ResumenScreen") {
                    ResumenScreen(
                        navController, regViewModel
                    )
                }
                composable("LoginScreen") {
                    LoginScreen(
                        navController, loginViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LevelUpGamerMovilTheme {
        Greeting("Android")
    }
}