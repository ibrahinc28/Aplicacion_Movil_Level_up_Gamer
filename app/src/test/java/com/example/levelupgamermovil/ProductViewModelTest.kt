package com.example.levelupgamermovil

import com.example.levelupgamermovil.MainDispatcherRule
import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.repository.ProductRepository
import com.example.levelupgamermovil.viewmodel.ProductViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()


    private lateinit var repository: ProductRepository
    private lateinit var viewModel: ProductViewModel

    @Before
    fun setup() {

        repository = mockk(relaxed = true)
    }

    @Test
    fun `cuando API falla (sin internet), debe cargar datos locales`() = runTest {



        coEvery { repository.obtenerProductosDeApi() } returns null


        val productoLocal = Producto(
            id = 1L,
            nombre = "Producto Local",
            description = "Descripción guardada",
            categoria = "Test",
            price = 5000.0,
            imagenPath = null,
            codigo = "LOC1"
        )
        val listaLocal = listOf(productoLocal)


        coEvery { repository.getProductos() } returns listaLocal


        viewModel = ProductViewModel(repository)




        assertTrue("La lista debería tener datos locales", viewModel.listaProductos.isNotEmpty())


        assertEquals("Producto Local", viewModel.listaProductos[0].nombre)


    }

    @Test
    fun `cuando API responde exito, debe actualizar la lista con datos de internet`() = runTest {
        // --- GIVEN ---
        val productoInternet = Producto(
            id = 2L,
            nombre = "Producto Internet",
            description = "Desc",
            categoria = "Web",
            price = 9990.0,
            imagenPath = "/web.png",
            codigo = "WEB1"
        )
        val listaInternet = listOf(productoInternet)


        coEvery { repository.obtenerProductosDeApi() } returns listaInternet


        viewModel = ProductViewModel(repository)


        assertEquals(1, viewModel.listaProductos.size)
        assertEquals("Producto Internet", viewModel.listaProductos[0].nombre)
    }
}