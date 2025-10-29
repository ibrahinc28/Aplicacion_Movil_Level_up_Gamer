package com.example.levelupgamermovil.view.screen

import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.levelupgamermovil.navigation.NavRoutes
import com.example.levelupgamermovil.view.components.ImagenInteligente
import com.example.levelupgamermovil.viewmodel.PerfilViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PerfilScreen(navController : NavController, viewModel: PerfilViewModel) {
    val context = LocalContext.current
    val imagenUri by viewModel.imagenUri.collectAsState()

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.setImage(uri)
    }
    var cameraUri by remember { mutableStateOf<Uri?>(null) }
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) viewModel.setImage(cameraUri)
    }
    fun createImageUri(context: Context): Uri {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
    val requestCameraPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageUri(context)
            cameraUri = uri
            takePictureLauncher.launch(uri)
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
        .padding(16.dp),
    contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Pedro Morales", style = MaterialTheme.typography.headlineMedium)
            ImagenInteligente(imagenUri)
        Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { pickImageLauncher.launch("image/*") }) {
                Text("Subir desde galería")
            }
        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick =  {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val uri = createImageUri(context)
                cameraUri = uri
                takePictureLauncher.launch(uri)
            } else {
                requestCameraPermission.launch(android.Manifest.permission.CAMERA)
            }
        }) {
            Text("Tomar foto desde cámara")
        }
            Button(
                onClick = {navController.navigate(NavRoutes.HOME)},
                colors = ButtonDefaults.buttonColors(
                    Color.Black,
                    Color.White
                )
            )
            {
                Text("Volver")
            }
        }
    }
}