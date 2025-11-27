package com.example.levelupgamermovil

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.levelupgamermovil.model.Cart
import com.example.levelupgamermovil.model.CartItem
import com.example.levelupgamermovil.view.CartScreen
import com.example.levelupgamermovil.viewmodel.CartViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Rule
import org.junit.Test

class CartScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun los_productos_del_carrito_se_muestran_en_la_pantalla(){

        val fakeCart = Cart(
            items = listOf(
                CartItem("1", "Teclado Gamer", 19990.0, 1),
                CartItem("2", "Mouse RGB", 9990.0, 2)
            )
        )

        val fakeViewModel = object : CartViewModel(){
            override val cart : StateFlow<Cart?> =
                MutableStateFlow(fakeCart)
        }

        composeRule.setContent {
            CartScreen(viewModel = fakeViewModel)
        }

        composeRule.onNodeWithText("Teclado Gamer").assertIsDisplayed()
        composeRule.onNodeWithText("Mouse RGB").assertIsDisplayed()
        composeRule.onNodeWithText("Cantidad: 2").assertIsDisplayed()
    }
}