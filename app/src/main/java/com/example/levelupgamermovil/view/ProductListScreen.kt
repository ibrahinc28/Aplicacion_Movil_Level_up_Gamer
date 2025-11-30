package com.example.levelupgamermovil.view

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.levelupgamermovil.viewmodel.ProductViewModel
import com.example.levelupgamermovil.viewmodel.CarritoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(navController: NavHostController,
                      productViewModel: ProductViewModel,
                      carritoViewModel: CarritoViewModel) {


    val isLoading = productViewModel.isLoading
    val error = productViewModel.mensajeError
    val products = productViewModel.listaProductos

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Productos") }
            )
        }
    ) { paddingValues ->
        // Manejo básico de estados de carga
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.fillMaxSize().wrapContentSize())
            } else if (error != null) {
                Text(text = "Error: $error", modifier = Modifier.fillMaxSize().wrapContentSize())
            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = products) { product ->
                        ProductItem(
                            product = product,
                            onAddToCart = { selectedProduct ->


                                carritoViewModel.agregarItem(

                                    codigoProducto = selectedProduct.id.toString(),


                                    nombre = selectedProduct.nombre,

                                    precio = selectedProduct.price
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}


