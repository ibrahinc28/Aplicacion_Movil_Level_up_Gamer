package com.example.levelupgamermovil

import com.example.levelupgamermovil.model.Cart
import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.repository.CartRepository
import com.example.levelupgamermovil.viewmodel.CartViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest

class CartViewModelTest : StringSpec({

    "loadCart() debe actualizar el StateFlow con los datos del carrito"{

        val fakeCart = Cart(
            items = listOf(
                CartItem(productId = "1", name = "Producto 1", price = 2000.0, quantity = 1)
            )
        )

        val fakeRepository = object : CartRepository(){
            override suspend fun getCart(): Cart {
                return fakeCart
            }
        }

        val viewModel = CartViewModel(repository = fakeRepository)

        runTest {
            viewModel.loadCart()
            viewModel.cart.value shouldBe fakeCart
        }
    }
})