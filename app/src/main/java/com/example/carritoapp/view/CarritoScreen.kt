package com.example.carritoapp.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.carritoapp.viewmodel.CarritoViewModel
import com.example.carritoapp.model.ItemCarrito
import com.example.carritoapp.viewmodel.ThemeViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    viewModel: CarritoViewModel,
    themeViewModel: ThemeViewModel,
    onContinuarComprando: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()
    val esOscuro by themeViewModel.esOscuro.collectAsState(initial = false)
    val topBarColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surface,
        animationSpec = tween(500), label = "TopBarColor"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor),
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ){
                        Text(
                            text = if (esOscuro) "Oscuro" else "Claro",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                        Spacer(Modifier.width(8.dp))
                        Switch(
                            checked = esOscuro,
                            onCheckedChange = { themeViewModel.alternarTema() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.primary,
                                checkedTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                            )
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Carrito de Compras",
                fontFamily = FontFamily.Monospace,
                color = Color.Green,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            Text(
                "Total de productos: ${estado.totalArticulos}",
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            if (estado.items.isEmpty()) {
                Text(
                    "Tu carrito está vacío.",
                    modifier = Modifier.padding(vertical = 32.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn(Modifier.weight(1f).fillMaxWidth().padding(vertical = 8.dp)) {
                    items(estado.items) { item ->
                        CarritoItemRow(
                            item = item,
                            onAumentar = { viewModel.aumentarCantidad(item.producto.codigo) },
                            onDisminuir = { viewModel.disminuirCantidad(item.producto.codigo) },
                            onEliminar = { viewModel.eliminarItem(item.producto.codigo) }
                        )
                        androidx.compose.material3.HorizontalDivider()
                    }
                }
            }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            "Subtotal: ${"%.2f".format(estado.subtotal)} CLP",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "Envío: ${"%.2f".format(estado.costoEnvio)} CLP",
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        androidx.compose.material3.HorizontalDivider()
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "Total a Pagar: ${"%.2f".format(estado.total)} CLP",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

            estado.mensajeConfirmado?.let { mensaje ->
                Text(
                    mensaje,
                    color = if (estado.compraFinalizada) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Row(
                Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = viewModel::vaciarCarrito,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan),
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Text("Vaciar Carrito", fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = viewModel::finalizarCompra,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Text("Finalizar Compra")
                }
            }
            Button(
                onClick = onContinuarComprando,
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Continuar Comprando")
            }
        }
    }
}

@Composable
fun CarritoItemRow(
    item: ItemCarrito,
    onAumentar: () -> Unit,
    onDisminuir: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(item.producto.nombre, style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onDisminuir, modifier = Modifier.size(32.dp)) { Text("-") }
                Spacer(Modifier.width(8.dp))
                Text("Cantidad: ${item.cantidad}")
                Spacer(Modifier.width(8.dp))
                Button(onClick = onAumentar, modifier = Modifier.size(32.dp)) { Text("+") }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${"%.2f".format(item.producto.precio * item.cantidad)} CLP",
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onEliminar) {
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar", tint = Color.Red)
            }
        }
    }
}


