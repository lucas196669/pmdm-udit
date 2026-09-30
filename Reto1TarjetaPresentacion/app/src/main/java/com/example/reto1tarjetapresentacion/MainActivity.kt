package com.example.reto1tarjetapresentacion

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reto1tarjetapresentacion.ui.theme.Reto1TarjetaPresentacionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aplicamos el tema de colores a todo lo de dentro
            Reto1TarjetaPresentacionTheme {
                // Surface = el "lienzo" de fondo que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Aquí llamamos a NUESTRA función, la que dibuja la tarjeta
                    TarjetaPresentacion()
                }
            }
        }
    }
}

@Composable
fun TarjetaPresentacion() {
    // LocalContext: así un Composable "pide prestado" el contexto de Android
    // Lo necesitamos para poder abrir el navegador y el correo desde los botones.
    val context = LocalContext.current

    // 1. COLUMN: apila los elementos de arriba a abajo
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // 2. IMAGE: la foto de perfil (archivo foto_perfil en res/drawable)
        Image(
            painter = painterResource(id = R.drawable.foto_perfil),
            contentDescription = "Foto de perfil de usuario",
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. TEXT: nombre
        Text(
            text = "Lucas Hernandez",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        // TEXT: rol o profesión
        Text(
            text = "Estudiante de DAM",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 4. BUTTON: enlace a GitHub
        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://github.com/lucas196669")
                )
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Mi Perfil de GitHub")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 5. BUTTON: enlace a LinkedIn
        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/lucas-hernandez-romero-b826b343a/")
                )
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Mi Perfil de LinkedIn")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 6. BUTTON: enviar un correo (Gmail)
        Button(
            onClick = {
                val intent = Intent(
                    Intent.ACTION_SENDTO,
                    Uri.parse("mailto:lucas.hernandez.romero2007@gmail.com")
                )
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Escríbeme por Gmail")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TarjetaPreview() {
    Reto1TarjetaPresentacionTheme {
        TarjetaPresentacion()
    }
}