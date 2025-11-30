package com.example.levelupgamermovil

import com.example.levelupgamermovil.model.Producto
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductoTest {

    @Test
    fun `getFullImageUrl debe agregar la url base si viene solo el path`() {

        val producto = Producto(
            id = 1L,
            codigo = "TEST",
            nombre = "Test",
            description = "Desc",
            categoria = "Cat",
            price = 100.0,
            imagenPath = "/images/foto.png"
        )


        val resultado = producto.getFullImageUrl()


        assertEquals("http://10.0.2.2:8080/images/foto.png", resultado)
    }

    @Test
    fun `getFullImageUrl no debe duplicar servidor si ya viene completo`() {

        val urlCompleta = "https://imgur.com/foto.jpg"
        val producto = Producto(
            id = 1L,
            codigo = "TEST",
            nombre = "Test",
            description = "Desc",
            categoria = "Cat",
            price = 100.0,
            imagenPath = urlCompleta
        )


        val resultado = producto.getFullImageUrl()


        assertEquals(urlCompleta, resultado)
    }
}