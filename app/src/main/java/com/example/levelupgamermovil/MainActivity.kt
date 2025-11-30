package com.example.levelupgamermovil


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.levelupgamermovil.model.AppDatabase
import com.example.levelupgamermovil.navigation.NavRoutes
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.repository.ProductRepository
import com.example.levelupgamermovil.repository.UsuariosGuardados
import com.example.levelupgamermovil.view.*
import com.example.levelupgamermovil.view.screen.PerfilScreen
import com.example.levelupgamermovil.viewmodel.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "levelupgamer-db"
        )
            .fallbackToDestructiveMigration()
            .build()

        val carritoDao = db.carritoDao()
        val productoDao = db.productoDao()

        val repoCarrito = CarritoRepository(carritoDao)
        val repoProducto = ProductRepository(productoDao)
        val usuariosGuardados = UsuariosGuardados()

        val loginViewModel: LoginViewModel by viewModels()
        val registroViewModel: RegistroViewModel by viewModels()
        val perfilViewModel: PerfilViewModel by viewModels()
        val themeViewModel: ThemeViewModel by viewModels()

        val carritoViewModel: CarritoViewModel by viewModels {
            CarritoViewModel.Factory(repoCarrito)
        }

        val productViewModel: ProductViewModel by viewModels {
            ProductViewModel.Factory(repoProducto)
        }

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = NavRoutes.HOME
            ) {
                composable(NavRoutes.LOGIN) {
                    LoginScreen(navController, loginViewModel, usuariosGuardados)
                }
                composable(NavRoutes.SIGNUP) {
                    userSignupScreen(navController, registroViewModel)
                }
                composable(NavRoutes.HOME) {
                    HomeScreen(
                        navController = navController,
                        productViewModel = productViewModel
                    )
                }
                composable(NavRoutes.PRODUCTOS) {
                    ProductListScreen(
                        navController = navController,
                        productViewModel = productViewModel,
                        carritoViewModel = carritoViewModel
                    )
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
                composable(NavRoutes.PERFIL) {
                    PerfilScreen(navController, perfilViewModel)
                }
            }
        }
    }
}
