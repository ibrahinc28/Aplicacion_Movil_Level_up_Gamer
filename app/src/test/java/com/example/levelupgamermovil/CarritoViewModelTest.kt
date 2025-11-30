package com.example.levelupgamermovil

import com.example.levelupgamermovil.model.CarritoEstado
import com.example.levelupgamermovil.model.CarritoItemEntity
import com.example.levelupgamermovil.model.ItemCarrito
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.viewmodel.CarritoViewModel
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest

class CarritoViewModelTest : StringSpec({

    val mockRepository = mockk<CarritoRepository>(relaxed = true)

    val initialItemsEntity = listOf(
        CarritoItemEntity(codigoProducto = "1", nombre = "Producto Test", precio = 2000.0, cantidad = 1)
    )

    coEvery { mockRepository.obtenerItemsCarrito() } returns MutableStateFlow(initialItemsEntity)

    "finalizarCompra() debe llamar al Repositorio para la compra remota y actualizar el estado a éxito" {
        runTest {
            coEvery { mockRepository.finalizarCompraRemota(any()) } returns Unit

            val viewModel = CarritoViewModel(repository = mockRepository)


            viewModel.finalizarCompra()

            coVerify(exactly = 1) { mockRepository.finalizarCompraRemota(any()) }

            viewModel.estado.value.mensajeConfirmado shouldBe "¡Gracias por tu compra! Tu pedido está en proceso."
            viewModel.estado.value.compraFinalizada shouldBe true
        }
    }

    "finalizarCompra() debe mostrar un mensaje de error si la llamada a la API falla" {
        runTest {
            coEvery { mockRepository.finalizarCompraRemota(any()) } throws Exception("API Fallida")

            val viewModel = CarritoViewModel(repository = mockRepository)

            viewModel.finalizarCompra()

            viewModel.estado.value.mensajeConfirmado shouldBe "Error de conexión al finalizar la compra."
            viewModel.estado.value.compraFinalizada shouldBe false
        }
    }

    "agregarItem() debe llamar al Repositorio para insertar una nueva entidad" {
        runTest {
            val viewModel = CarritoViewModel(repository = mockRepository)

            viewModel.agregarItem("5", "Nuevo Producto", 5000.0)

            coVerify(exactly = 1) { mockRepository.insertar(any()) }
        }
    }
})