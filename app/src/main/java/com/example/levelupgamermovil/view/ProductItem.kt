package com.example.levelupgamermovil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.res.painterResource
import com.example.levelupgamermovil.model.Producto

@Composable
fun ProductItem(
    product: Producto,
    onAddToCart: (Producto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            AsyncImage(
                model = product.getFullImageUrl(),
                contentDescription = product.nombre,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop,

                placeholder = painterResource(android.R.drawable.ic_menu_gallery),

                error = painterResource(android.R.drawable.ic_delete)
            )

            Spacer(modifier = Modifier.height(8.dp))


            Text(text = product.nombre, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Precio: $${"%.0f".format(product.price)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onAddToCart(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
