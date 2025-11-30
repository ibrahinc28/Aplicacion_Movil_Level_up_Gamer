package com.example.levelupgamermovil.androidTest
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import com.example.levelupgamermovil.model.CarritoEstado
import com.example.levelupgamermovil.model.ItemCarrito
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.view.CarritoScreen
import com.example.levelupgamermovil.viewmodel.CarritoViewModel
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import io.mockk.mockk

class CarritoScreenTest {
    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun los_productos_del_carrito_se_muestran_en_la_pantalla_y_totales_correctos(){

        val subtotalEsperado = (19990.0 * 1) + (9990.0 * 2)
        val costoEnvioEsperado = 5000.0

        val fakeEstado = CarritoEstado(
            items = listOf(
                ItemCarrito(codigoProducto = "1", nombre = "Teclado Gamer", precio = 19990.0, cantidad = 1),
                ItemCarrito(codigoProducto = "2", nombre = "Mouse RGB", precio = 9990.0, cantidad = 2)
            ),
            totalArticulos = 3,
            subtotal = subtotalEsperado,
            costoEnvio = costoEnvioEsperado,
            total = subtotalEsperado + costoEnvioEsperado
        )

        val fakeViewModel = object : CarritoViewModel(mockk<CarritoRepository>(relaxed = true)){
            override val estado : StateFlow<CarritoEstado> =
                MutableStateFlow(fakeEstado)

        }

        composeRule.setContent {
            CarritoScreen(
                viewModel = fakeViewModel,
                onContinuarComprando = {}
            )
        }

        composeRule.onNodeWithText("Teclado Gamer").assertIsDisplayed()
        composeRule.onNodeWithText("Mouse RGB").assertIsDisplayed()
        composeRule.onNodeWithText("Cantidad: 2").assertIsDisplayed()
        composeRule.onNodeWithText("Total de productos: 3").assertIsDisplayed()
        composeRule.onNodeWithText("Subtotal: 39970.00 CLP").assertIsDisplayed()
        composeRule.onNodeWithText("Envío: 5000.00 CLP").assertIsDisplayed()
        composeRule.onNodeWithText("Total a Pagar: 44970.00 CLP").assertIsDisplayed()
        composeRule.onNodeWithText("Finalizar Compra").assertIsDisplayed()
    }
}