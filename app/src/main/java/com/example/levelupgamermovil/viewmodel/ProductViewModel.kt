package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _productoList = MutableStateFlow<List<Producto>>(emptyList())
    val productoList: StateFlow<List<Producto>> = _productoList

    init { cargarProductos() }

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
}
