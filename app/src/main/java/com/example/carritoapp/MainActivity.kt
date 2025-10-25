package com.example.carritoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carritoapp.ui.theme.CarritoAppTheme
import com.example.carritoapp.view.CarritoScreen
import com.example.carritoapp.viewmodel.CarritoViewModel
import com.example.carritoapp.viewmodel.ThemeViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.carritoapp.model.AppDatabase
import com.example.carritoapp.repository.CarritoRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel(
                factory = ThemeViewModel.Factory(application)
            )
            val esOscuro by themeViewModel.esOscuro.collectAsState(initial = false)

            CarritoAppTheme(darkTheme = esOscuro) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context = LocalContext.current

                    val db = remember{
                        AppDatabase.getDataBase(context)
                    }

                    val repository = remember { CarritoRepository(db.carritoDao())
                    }

                    val carritoViewModel: CarritoViewModel = viewModel(
                        factory = CarritoViewModel.Factory(repository)
                    )

                    CarritoAppContent(
                        viewModel = carritoViewModel,
                        themeViewModel = themeViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun CarritoAppContent(
    viewModel: CarritoViewModel = viewModel(),
    themeViewModel: ThemeViewModel
){
    CarritoScreen(viewModel = viewModel, themeViewModel = themeViewModel)
}

@Preview(showBackground = true)
@Composable
fun AppPreview(){
    CarritoAppTheme {
    }
}

