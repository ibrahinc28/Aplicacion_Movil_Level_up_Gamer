package com.example.levelupgamermovil

import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.model.ProductoDao
import com.example.levelupgamermovil.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductRepositoryTest {


    private val mockDao = mockk<ProductoDao>(relaxed = true)


    private val repository = ProductRepository(mockDao)

    @Test
    fun `getProductos debe llamar al DAO`() = runTest {

        val listaFicticia = listOf(Producto(1L, "C", "N", "D", "Cat", 10.0, null))
        coEvery { mockDao.getAll() } returns listaFicticia


        val resultado = repository.getProductos()


        assertEquals(listaFicticia, resultado)

        coVerify(exactly = 1) { mockDao.getAll() }
    }

    @Test
    fun `insertarProducto debe llamar al DAO`() = runTest {

        val producto = Producto(1L, "C", "N", "D", "Cat", 10.0, null)


        repository.insertarProducto(producto)


        coVerify(exactly = 1) { mockDao.insert(producto) }
    }

    @Test
    fun `insertarLista debe guardar multiples productos en el DAO`() = runTest {

        val lista = listOf(
            Producto(1L, "A", "N", "D", "C", 10.0, null),
            Producto(2L, "B", "N", "D", "C", 20.0, null)
        )


        repository.insertarLista(lista)


        coVerify(exactly = 1) { mockDao.insertAll(lista) }
    }
}