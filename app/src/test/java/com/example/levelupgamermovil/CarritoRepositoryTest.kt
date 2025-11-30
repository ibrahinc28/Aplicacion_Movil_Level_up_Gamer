package com.example.levelupgamermovil.test

import com.example.levelupgamermovil.model.CarritoDao
import com.example.levelupgamermovil.model.ItemCarrito
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.remote.ApiService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import com.example.levelupgamermovil.model.CarritoEstado

class CarritoRepositoryTest : StringSpec({

    val mockDao = mockk<CarritoDao>(relaxed = true)
    val mockApi = mockk<ApiService>(relaxed = true)

    val repo = CarritoRepository(mockDao, mockApi)

    val itemsFake = listOf(
        ItemCarrito(codigoProducto = "1", nombre = "Producto 1", precio = 1000.0, cantidad = 2)
    )

    "finalizarCompraRemota() debe llamar al microservicio y vaciar la base de datos local si tiene éxito" {
        runTest {
            coEvery { mockApi.clearCart() } throws Exception("Error de conexión simulado")

            repo.finalizarCompraRemota(itemsFake)

            coVerify(exactly = 1) { mockApi.clearCart() }
            coVerify(exactly = 1) { mockDao.vaciar() }
        }
    }

    "finalizarCompraRemota() NO debe vaciar el DAO si la llamada al microservicio falla" {
        runTest {
            coEvery { mockApi.clearCart() } throws Exception("Error de conexión simulado")

            kotlin.runCatching {
                repo.finalizarCompraRemota(itemsFake)
            }

            coVerify(exactly = 1) { mockApi.clearCart() }
            coVerify(exactly = 0) { mockDao.vaciar() }
        }
    }
})