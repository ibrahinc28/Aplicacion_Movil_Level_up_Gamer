package com.example.levelupgamermovil.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.network.RetrofitClient
import com.example.levelupgamermovil.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {


    var listaProductos by mutableStateOf<List<Producto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var mensajeError by mutableStateOf<String?>(null)
        private set

    var juegosGratis by mutableStateOf<List<GameNews>>(emptyList())
        private set

    init {

        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            isLoading = true
            val productosInternet = repository.obtenerProductosDeApi()

            if (productosInternet != null) {
                listaProductos = productosInternet
                repository.insertarLista(productosInternet)
            } else {
                mensajeError = "Usando datos locales"
                cargarDesdeLocal()
            }
            isLoading = false
        }
    }


    private suspend fun cargarDesdeLocal() {
        val locales = repository.getProductos()
        if (locales.isNotEmpty()) {
            listaProductos = locales
        }
    }


    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            try {

                val resp = RetrofitClient.webService.crearProducto(producto)
                if (resp.isSuccessful) {

                    cargarProductos()
                }
            } catch (e: Exception) {
                mensajeError = "No se pudo crear: ${e.message}"
            }
        }
    }

    fun cargarNoticiasGamer() {
        viewModelScope.launch {
            try {
                // Usamos el cliente EXTERNO, no el de tu backend
                val response = com.example.levelupgamermovil.network.ExternalRetrofitClient.service.getFreeGames()
                if (response.isSuccessful) {
                    // Tomamos solo los primeros 5 para no saturar
                    juegosGratis = response.body()?.take(5) ?: emptyList()
                }
            } catch (e: Exception) {
                // Si falla la API externa, no importa, no bloquea la app principal
                println("Fallo API Externa: ${e.message}")
            }
        }
    }


    companion object {
        fun Factory(repository: ProductRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { ProductViewModel(repository) }
        }
    }
}
