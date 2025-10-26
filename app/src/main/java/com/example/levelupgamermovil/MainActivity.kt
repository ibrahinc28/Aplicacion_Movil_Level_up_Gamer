package com.example.levelupgamermovil


import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelupgamermovil.navigation.NavRoutes
import com.example.levelupgamermovil.view.LoginScreen
import com.example.levelupgamermovil.view.userSignupScreen
import com.example.levelupgamermovil.view.HomeScreen
import com.example.levelupgamermovil.view.ProductListScreen
import com.example.levelupgamermovil.view.CarritoScreen
import com.example.levelupgamermovil.view.ResumenScreen
import com.example.levelupgamermovil.viewmodel.LoginViewModel
import com.example.levelupgamermovil.viewmodel.RegistroViewModel
import com.example.levelupgamermovil.repository.UsuariosGuardados
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.viewmodel.CarritoViewModel
import com.example.levelupgamermovil.viewmodel.ThemeViewModel
import com.example.levelupgamermovil.model.AppDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val loginViewModel: LoginViewModel = viewModels<LoginViewModel>().value
            val registroViewModel: RegistroViewModel = viewModels<RegistroViewModel>().value
            val usuariosGuardados = UsuariosGuardados()
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "levelupgamer-db"
            ).build()

            val carritoDao = db.carritoDao()
            val repo = CarritoRepository(carritoDao)
            val carritoViewModel: CarritoViewModel by viewModels { CarritoViewModel.Factory(repo) }

            val themeViewModel: ThemeViewModel = viewModels<ThemeViewModel>().value

            NavHost(
                navController = navController,
                startDestination = NavRoutes.LOGIN
            ) {
                composable(NavRoutes.LOGIN) {
                    LoginScreen(navController, loginViewModel, usuariosGuardados)
                }
                composable(NavRoutes.SIGNUP) {
                    userSignupScreen(navController, registroViewModel)
                }
                composable(NavRoutes.HOME) {
                    HomeScreen(navController)
                }
                composable(NavRoutes.PRODUCTOS) {
                    ProductListScreen(navController)
                }
                composable(NavRoutes.CARRITO) {
                    CarritoScreen(
                        viewModel = carritoViewModel,
                        themeViewModel = themeViewModel,
                        onContinuarComprando = { navController.navigate(NavRoutes.PRODUCTOS) }
                    )
                }
                composable(NavRoutes.RESUMEN) {
                    ResumenScreen(navController, registroViewModel, usuariosGuardados)
                }
            }
        }
    }
}
