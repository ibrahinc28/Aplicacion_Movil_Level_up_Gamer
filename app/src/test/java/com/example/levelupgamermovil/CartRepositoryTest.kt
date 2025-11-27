package com.example.levelupgamermovil

import com.example.levelupgamermovil.model.Cart
import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.remote.ApiService
import com.example.levelupgamermovil.repository.CartRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class TestableCartRepository(private val testApi: ApiService) : CartRepository(api = testApi)

class CartRepositoryTest : StringSpec({

    "getCart() debe retornar un carrito simulado"{

        val fakeCart = Cart(
            items = listOf(
                CartItem(productId = "1", name = "Producto 1", price = 1000.0, quantity = 2),
                CartItem(productId = "2", name = "Producto 2", price = 500.0, quantity = 1)
            )
        )

        val mockApi = mockk<ApiService>()
        coEvery { mockApi.getCart() } returns fakeCart

        val repo = TestableCartRepository(mockApi)

        runTest {
            val result = repo.getCart()
            result shouldBe fakeCart
        }
    }
})