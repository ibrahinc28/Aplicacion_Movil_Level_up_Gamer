package com.example.levelupgamermovil.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.levelupgamermovil.model.Cart
import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class CartViewModel (
    private val repository: CartRepository = CartRepository()
) : ViewModel(){
    protected val _cart = MutableStateFlow<Cart?>(null)

    open val cart: StateFlow<Cart?> = _cart

    init {
        loadCart()
    }

    fun loadCart(){
        viewModelScope.launch {
            try {
                _cart.value = repository.getCart()
            }catch (e: Exception){
                println("Error: ${e.localizedMessage}")
            }
        }
    }

    fun addToCart(item: CartItem){
        viewModelScope.launch {
            _cart.value = repository.addItem(item)
        }
    }

    fun removeFromCart(id: String){
        viewModelScope.launch {
            _cart.value = repository.removeItem(id)
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            _cart.value = repository.clearCart()
        }
    }
}