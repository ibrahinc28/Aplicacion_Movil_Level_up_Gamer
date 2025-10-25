package com.example.carritoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.carritoapp.model.CarritoEstado
import com.example.carritoapp.model.CarritoItemEntity
import com.example.carritoapp.model.ItemCarrito
import com.example.carritoapp.model.Producto
import com.example.carritoapp.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CarritoViewModel(private val repository: CarritoRepository) : ViewModel() {

    private val _mensajeFrow = MutableStateFlow<String?>(null)
    private val _compraFinalizada = MutableStateFlow(false)

    private val ITEMS_FLOW = repository.obtenerItemsCarrito()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    val estado: StateFlow<CarritoEstado> = ITEMS_FLOW.combine(_mensajeFrow) { itemsEntity, mensaje -> itemsEntity to mensaje }
        .combine(_compraFinalizada){ (itemsEntity, mensaje), finalizada ->
        val items = itemsEntity.map {
            ItemCarrito(
                producto = Producto(it.codigoProducto, it.nombre, it.precio),
                cantidad = it.cantidad
            )
        }

        val nuevoSubtotal = items.sumOf { it.producto.precio * it.cantidad }
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

    fun agregarItem(producto: Producto) {
        viewModelScope.launch {
            _mensajeFrow.value = null
            val itemExistente = ITEMS_FLOW.value.find { it.codigoProducto == producto.codigo }

            val nuevoItemEntity = if (itemExistente != null) {
                itemExistente.copy(cantidad = itemExistente.cantidad + 1)
            } else {
                CarritoItemEntity(
                    codigoProducto = producto.codigo,
                    nombre = producto.nombre,
                    precio = producto.precio,
                    cantidad = 1
                )
            }

            repository.insertar(nuevoItemEntity)
            _mensajeFrow.value = "Producto ${producto.nombre} agregado al carrito!"
        }
    }
    fun aumentarCantidad(codigo: String) {
        viewModelScope.launch {
            val item = ITEMS_FLOW.value.find { it.codigoProducto == codigo }
            if (item != null) {
                repository.actualizar(item.copy(cantidad = item.cantidad + 1))
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
            }
        }
    }

    fun eliminarItem(codigo: String) {
        viewModelScope.launch {
            repository.eliminarPorCodigo(codigo)
        }
    }

    fun vaciarCarrito() {
        viewModelScope.launch {
            repository.vaciar()
            _mensajeFrow.value = "El carrito ha sido vaciado."
            _compraFinalizada.value = false
        }
    }

    fun finalizarCompra() {
        if (ITEMS_FLOW.value.isEmpty()) {
            _mensajeFrow.value = "Tu carrito está vacío. Añade productos para continuar."
            _compraFinalizada.value = false
            return
        }
        viewModelScope.launch {
            repository.vaciar()
            _mensajeFrow.value = "¡Gracias por tu compra! Tu pedido está en proceso."
            _compraFinalizada.value = true
        }
    }
    companion object {
        fun Factory(repository: CarritoRepository): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CarritoViewModel(repository)
            }
        }
    }
}
