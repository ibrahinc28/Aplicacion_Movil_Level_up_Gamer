package com.example.levelupgamermovil.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.levelupgamermovil.viewmodel.CartViewModel
import com.example.levelupgamermovil.model.CartItem

@Composable
fun CartScreen(viewModel: CartViewModel = viewModel()) {
    val cart = viewModel.cart.collectAsState().value
    val items = cart?.items ?: emptyList()
    val subtotal = items.sumOf { it.price * it.quantity }
    val totalItems = items.sumOf { it.quantity }
    val shippingCost = if (items.isNotEmpty()) 5000.0 else 0.0
    val total = subtotal + shippingCost

    Column(modifier = Modifier.Companion.fillMaxSize().padding(16.dp)) {
        Text("Carrito de Compras", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.Companion.height(16.dp))

        if (items.isEmpty()) {
            Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
        } else {
            LazyColumn(modifier = Modifier.Companion.weight(1f)) {
                items(items) { item ->
                    CartItemRow(
                        item = item,
                        onAdd = { viewModel.addToCart(item) },
                        onRemove = { viewModel.removeFromCart(item.productId) }
                    )
                    Divider()
                }
            }

            Spacer(Modifier.Companion.height(16.dp))

            Text("Total de artículos: $totalItems")
            Text("Subtotal: ${"%.2f".format(subtotal)} CLP")
            Text("Envío: ${"%.2f".format(shippingCost)} CLP")
            Text("Total a pagar: ${"%.2f".format(total)} CLP")

            Spacer(Modifier.Companion.height(16.dp))

            Button(
                onClick = { viewModel.clearCart() },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Vaciar Carrito")
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Column {
            Text(item.name, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.Companion.CenterVertically) {
                Button(onClick = onRemove, modifier = Modifier.Companion.size(32.dp)) { Text("-") }
                Spacer(Modifier.Companion.width(8.dp))
                Text("Cantidad: ${item.quantity}")
                Spacer(Modifier.Companion.width(8.dp))
                Button(onClick = onAdd, modifier = Modifier.Companion.size(32.dp)) { Text("+") }
            }
        }
        Text(
            "${"%.2f".format(item.price * item.quantity)} CLP",
            style = MaterialTheme.typography.titleMedium
        )
    }
}