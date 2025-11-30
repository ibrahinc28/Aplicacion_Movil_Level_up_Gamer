package com.example.levelupgamermovil.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.levelupgamermovil.model.AppDatabase
import com.example.levelupgamermovil.model.CarritoEstado
import com.example.levelupgamermovil.model.CarritoItemEntity
import com.example.levelupgamermovil.model.ItemCarrito
import com.example.levelupgamermovil.repository.CarritoRepository
import com.example.levelupgamermovil.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    private val _mensajeFrow = MutableStateFlow<String?>(null)
    private val _compraFinalizada = MutableStateFlow(false)
    private val COSTO_ENVIO = 5000.0

    private val ITEMS_FLOW = repository.obtenerItemsCarrito()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    open val estado: StateFlow<CarritoEstado> = ITEMS_FLOW.combine(_mensajeFrow) { itemsEntity, mensaje -> itemsEntity to mensaje }
        .combine(_compraFinalizada){ (itemsEntity, mensaje), finalizada ->
            val items = itemsEntity.map { entity: CarritoItemEntity ->
                ItemCarrito(
                    codigoProducto = entity.codigoProducto,
                    nombre = entity.nombre,
                    precio = entity.precio,
                    cantidad = entity.cantidad
                )
            }

            val nuevoSubtotal = items.sumOf { it.precio * it.cantidad }
            val nuevoCostoEnvio = if (items.isNotEmpty()) 5000.0 else 0.0
            val nuevoTotal = nuevoSubtotal + nuevoCostoEnvio
            val nuevoTotalArticulos = items.sumOf { it.cantidad }

            CarritoEstado(
                items = items,
                subtotal = nuevoSubtotal,
                costoEnvio = nuevoCostoEnvio,
                total = nuevoTotal,
                totalArticulos = nuevoTotalArticulos,
                mensajeConfirmado = mensaje,
                compraFinalizada = finalizada
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CarritoEstado()

        )

    init {
        sincronizarCarritoInicial()
    }

    private fun sincronizarCarritoInicial() {
        viewModelScope.launch {
            try {
                val carritoRemoto = repository.obtenerCarritoRemoto()

            } catch (e: Exception) {
                _mensajeFrow.value = "Error al sincronizar el carrito remoto."
            }
        }
    }

    fun agregarItem(codigoProducto: String, nombre: String, precio: Double) {
        viewModelScope.launch {
            _mensajeFrow.value = null

            val itemExistente = ITEMS_FLOW.value.find { it.codigoProducto == codigoProducto }

            val nuevoItemEntity = if (itemExistente != null) {
                itemExistente.copy(cantidad = itemExistente.cantidad + 1)
            } else {
                CarritoItemEntity(
                    codigoProducto = codigoProducto,
                    nombre = nombre,
                    precio = precio,
                    cantidad = 1
                )
            }

            try {
                repository.insertar(nuevoItemEntity)

                val itemParaApi = ItemCarrito(codigoProducto, nombre, precio, 1)
                repository.agregarItemRemoto(itemParaApi)

                _mensajeFrow.value = "Producto $nombre agregado al carrito!"
            } catch (e: Exception) {
                _mensajeFrow.value = "Error al agregar el producto remotamente."
                println("Error al agregar item: ${e.message}")
            }
        }
    }

    fun aumentarCantidad(codigo: String) {
        viewModelScope.launch {
            val item = ITEMS_FLOW.value.find { it.codigoProducto == codigo }
            if (item != null) {
                repository.actualizar(item.copy(cantidad = item.cantidad + 1))
                try {
                    val itemParaApi = ItemCarrito(item.codigoProducto, item.nombre, item.precio, 1)
                    repository.agregarItemRemoto(itemParaApi)
                } catch (e: Exception) {
                    _mensajeFrow.value = "Error al aumentar cantidad remotamente."
                }
            }
        }
    }

    fun disminuirCantidad(codigo: String) {
        viewModelScope.launch {
            val item = ITEMS_FLOW.value.find { it.codigoProducto == codigo }
            if (item != null){
                if(item.cantidad > 1){
                    repository.actualizar(item.copy(cantidad = item.cantidad - 1))
                }else{
                    repository.eliminarPorCodigo(codigo)
                }

                try {
                    repository.eliminarItemRemoto(codigo)
                } catch (e: Exception) {
                    _mensajeFrow.value = "Error al disminuir/eliminar remotamente."
                }
            }
        }
    }
    fun eliminarItem(codigo: String) {
        viewModelScope.launch {
            repository.eliminarPorCodigo(codigo)
            try {
                repository.eliminarItemRemoto(codigo)
            } catch (e: Exception) {
                _mensajeFrow.value = "Error al eliminar el item remotamente."
            }
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.vaciarLocal()
            _mensajeFrow.value = "El carrito se ha vaciado."
            _compraFinalizada.value = false
        }
    }

    fun finalizarCompra() {
        val currentItems = estado.value.items

        if (currentItems.isEmpty()) {
            _mensajeFrow.value = "Tu carrito está vacío. Añade productos para continuar."
            _compraFinalizada.value = false
            return
        }

        viewModelScope.launch {
            try {
                repository.finalizarCompraRemota(currentItems)

                _mensajeFrow.value = "¡Gracias por tu compra! Tu pedido está en proceso."
                _compraFinalizada.value = true

            } catch (e: Exception) {
                _mensajeFrow.value = "Error de conexión al finalizar la compra."
                _compraFinalizada.value = false
                println("Error de compra: ${e.message}")
            }
        }
    }

    companion object {
        fun Factory(context: Context): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val db = AppDatabase.getDataBase(context)
                val dao = db.carritoDao()
                val apiService = RetrofitInstance.api

                val repository = CarritoRepository(dao, apiService)

                CarritoViewModel(repository)
            }
        }
    }
}