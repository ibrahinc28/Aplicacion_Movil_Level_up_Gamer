package com.example.levelupgamermovil.repository

import com.example.levelupgamermovil.model.Producto
import com.example.levelupgamermovil.model.ProductoDao
import com.example.levelupgamermovil.R

class ProductRepository(private val productoDao: ProductoDao) {

    suspend fun getProductos(): List<Producto> = productoDao.getAll()

    suspend fun insertarProducto(producto: Producto) = productoDao.insert(producto)

    suspend fun inicializarProductosSiVacio() {
        val actuales = productoDao.getAll()
        if (actuales.isEmpty()) {
            val iniciales = listOf(
                Producto(
                    id = "JM001",
                    name = "Catan",
                    description = "Clásico juego de estrategia donde los jugadores compiten por colonizar y expandirse en la isla de Catan. Ideal para 3-4 jugadores y perfecto para noches de juego en familia o con amigos.",
                    price = 29.990,
                    imageRes = R.drawable.catan
                ),
                Producto(
                    id = "AC001",
                    name = "Controlador Inalámbrico Xbox Series X",
                    description = "Un juego de colocación de fichas donde los jugadores construyen el paisaje alrededor de la fortaleza medieval de Carcassonne. Ideal para 2-5 jugadores y fácil de aprender.",
                    price = 24.990,
                    imageRes = R.drawable.controlxbox
                ),
                Producto(
                    id = "CO001",
                    name = "PlayStation 5",
                    description = "La consola de última generación de Sony, que ofrece gráficos impresionantes y tiempos de carga ultrarrápidos para una experiencia de juego inmersiva.",
                    price = 549.990,
                    imageRes = R.drawable.playstation5
                ),
                Producto(
                    id = "CG001",
                    name = "PC Gamer ASUS ROG Strix",
                    description = "Un potente equipo diseñado para los gamers más exigentes, equipado con los últimos componentes para ofrecer un rendimiento excepcional en cualquier juego.",
                    price = 1299.990,
                    imageRes = R.drawable.pcgamer
                ),
                Producto(
                    id = "SG001",
                    name = "Silla Gamer Secretlab Titan",
                    description = "Diseñada para el máximo confort, esta silla ofrece un soporte ergonómico y personalización ajustable para sesiones de juego prolongadas.",
                    price = 349.990,
                    imageRes = R.drawable.sillagamer
                )
            )
            productoDao.insertAll(iniciales)
        }
    }
}
