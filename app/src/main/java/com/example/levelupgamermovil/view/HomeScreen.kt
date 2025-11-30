package com.example.levelupgamermovil.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.levelupgamermovil.R
import com.example.levelupgamermovil.navigation.NavRoutes
import com.example.levelupgamermovil.viewmodel.ProductViewModel
import com.example.levelupgamermovil.model.GameNews
import androidx.compose.foundation.lazy.items
import kotlin.collections.isNotEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, productViewModel: ProductViewModel = viewModel()) {
    val juegosGratis: List<GameNews> = productViewModel.juegosGratis

    LaunchedEffect(Unit) {
        productViewModel.cargarNoticiasGamer()
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Level Up Gamer") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo App",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(16.dp))

            if (juegosGratis.isNotEmpty()) {
                Text(
                    text = "🔥 Juegos Gratis Destacados (API)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(Modifier.height(8.dp))


                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(juegosGratis) { game ->
                        Card(
                            modifier = Modifier
                                .width(160.dp)
                                .height(200.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column {

                                AsyncImage(
                                    model = game.thumbnail,
                                    contentDescription = game.title,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    contentScale = ContentScale.Crop,
                                    placeholder = painterResource(android.R.drawable.ic_menu_gallery),
                                    error = painterResource(android.R.drawable.ic_delete)
                                )

                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = game.title,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = game.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(Modifier.height(24.dp))
            } else {

            }

            Text(text = "Menú principal", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

            Button(
                onClick = { navController.navigate(NavRoutes.PRODUCTOS) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color.Green)
            ) {
                Text("Productos")
            }

            Button(
                onClick = { navController.navigate(NavRoutes.CARRITO) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF2196F3))
            ) {
                Text("Carrito")
            }

            Button(
                onClick = { navController.navigate(NavRoutes.LOGIN) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF8E24AA))
            ) {
                Text("Usuarios")
            }

            Button(onClick = {navController.navigate("UsuarioAPIScreen")},
                colors = ButtonDefaults.buttonColors(
                    Color.Red,
                    Color.White
                )
            ) {
                Text("Usuarios registrados")
            }
        }
    }
}
