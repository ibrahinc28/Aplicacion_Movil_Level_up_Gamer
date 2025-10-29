package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _productoList = MutableStateFlow<List<Producto>>(emptyList())
    val productoList: StateFlow<List<Producto>> = _productoList

    init {  viewModelScope.launch {
        repository.inicializarProductosSiVacio()
        cargarProductos()
    }}

    fun cargarProductos() {
        viewModelScope.launch {
            _productoList.value = repository.getProductos()
        }
    }

    fun agregarProducto(producto: Producto) {
        viewModelScope.launch {
            repository.insertarProducto(producto)
            cargarProductos()
        }
    }

    companion object {
        fun Factory(repository: ProductRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer { ProductViewModel(repository) }
        }
    }
}
