package com.example.reto1tarjetapresentacion

// ───────────── IMPORTS ─────────────
// Android básico: abrir enlaces/correo (Intent, Uri) y ciclo de vida de la pantalla (Bundle)
import android.content.Intent
import android.net.Uri
import android.os.Bundle
// Activity de Compose y función que dibuja la interfaz dentro de ella
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Elementos visuales: Canvas (dibujar), Image (imagen), background (fondo), border (borde)
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
// Contenedores y medidas: Box (capas), Column (vertical), Row (horizontal), Spacer (hueco), etc.
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
// Formas: círculo y rectángulo con esquinas redondeadas
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
// Componentes Material 3: botón, colores del botón, tema, lienzo de fondo y texto
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
// Estado y efectos: permiten que la pantalla cambie con el tiempo (animaciones)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
// Utilidades de interfaz: alineación, modificadores, recorte, posiciones, colores, etc.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Tema propio del proyecto
import com.example.reto1tarjetapresentacion.ui.theme.Reto1TarjetaPresentacionTheme
// delay: pausa dentro de una corrutina (lo usamos para las animaciones)
import kotlinx.coroutines.delay

// ───────────── ACTIVITY PRINCIPAL ─────────────
// Es la "puerta de entrada" de la app: lo primero que Android ejecuta al abrirla.
class MainActivity : ComponentActivity() {
    // onCreate se ejecuta una sola vez, cuando se crea la pantalla.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent: aquí dentro se define toda la interfaz con Compose.
        setContent {
            // Aplicamos el tema de colores a todo lo de dentro
            Reto1TarjetaPresentacionTheme {
                // Surface = el "lienzo" de fondo que ocupa toda la pantalla
                Surface(
                    modifier = Modifier.fillMaxSize(),          // ocupa todo el ancho y alto
                    color = MaterialTheme.colorScheme.background // color de fondo del tema
                ) {
                    // Aquí llamamos a NUESTRA función, la que dibuja la tarjeta
                    TarjetaPresentacion()
                }
            }
        }
    }
}

// ───────────── PANTALLA DE LA TARJETA ─────────────
// @Composable indica que esta función dibuja interfaz en vez de devolver un valor.
@Composable
fun TarjetaPresentacion() {
    // LocalContext: así un Composable "pide prestado" el contexto de Android
    // Lo necesitamos para poder abrir el navegador y el correo desde los botones.
    val context = LocalContext.current

    // Color verde "terminal" que reutilizamos en todo el diseño.
    // Está guardado en una variable para cambiarlo en un solo sitio.
    // 0xFF = opacidad total, 00FF9C = el color en hexadecimal.
    val verdeTerminal = Color(0xFF00FF9C)

    // --- EFECTO MÁQUINA DE ESCRIBIR ---
    // Texto final que queremos que aparezca.
    val textoCompleto = "Estudiante de DAM"
    // Estado: lo que se ve en pantalla ahora mismo. Empieza vacío.
    // remember = guarda el valor aunque Compose redibuje la pantalla.
    // mutableStateOf = cuando cambia, Compose repinta automáticamente el texto.
    var textoVisible by remember { mutableStateOf("") }
    // Estado: si el cursor "_" está encendido o apagado.
    var cursorVisible by remember { mutableStateOf(true) }

    // LaunchedEffect: ejecuta código una vez cuando el Composable aparece.
    // Va añadiendo una letra más cada 90 milisegundos.
    LaunchedEffect(Unit) {
        for (i in 1..textoCompleto.length) {
            // substring(0, i) = las primeras "i" letras del texto
            textoVisible = textoCompleto.substring(0, i)
            delay(90) // espera 90 ms antes de la siguiente letra
        }
    }

    // Segundo efecto: enciende y apaga el cursor cada medio segundo.
    LaunchedEffect(Unit) {
        while (true) { // bucle infinito mientras la pantalla esté visible
            delay(500)
            cursorVisible = !cursorVisible // invierte el valor: true -> false -> true...
        }
    }

    // Lista de tecnologías para las etiquetas. Para añadir otra, solo súmala aquí.
    val tecnologias = listOf("Kotlin", "Compose", "Android", "Git")

    // BOX: permite poner capas una encima de otra (fondo + contenido)
    // Lo que se escribe después queda POR ENCIMA de lo anterior.
    Box(
        modifier = Modifier
            .fillMaxSize() // ocupa toda la pantalla
            // Degradado vertical: de negro a azul noche
            .background(
                Brush.verticalGradient(
                    // Lista de colores de arriba a abajo
                    colors = listOf(
                        Color(0xFF000000), // negro arriba
                        Color(0xFF0B1426), // azul noche en el medio
                        Color(0xFF0D2B2B)  // verde oscuro abajo
                    )
                )
            )
    ) {
        // CAPA 1: cuadrícula tipo circuito dibujada con Canvas
        // Canvas = un lienzo donde dibujamos líneas y formas con código.
        Canvas(modifier = Modifier.fillMaxSize()) {
            val paso = 40.dp.toPx() // separación entre líneas (40 dp convertidos a píxeles)
            val colorLinea = verdeTerminal.copy(alpha = 0.08f) // verde casi transparente

            // Líneas verticales: empezamos en x = 0 y avanzamos de 40 en 40 hasta el borde
            var x = 0f
            while (x < size.width) {
                drawLine(
                    color = colorLinea,
                    start = Offset(x, 0f),           // punto de arriba
                    end = Offset(x, size.height),    // punto de abajo
                    strokeWidth = 1f                 // grosor de la línea
                )
                x += paso
            }

            // Líneas horizontales: igual, pero avanzando en y
            var y = 0f
            while (y < size.height) {
                drawLine(
                    color = colorLinea,
                    start = Offset(0f, y),           // punto de la izquierda
                    end = Offset(size.width, y),     // punto de la derecha
                    strokeWidth = 1f
                )
                y += paso
            }
        }

        // CAPA 2: código binario decorativo arriba
        Text(
            // "\n" = salto de línea; el "+" une varios trozos de texto
            text = "01001100 01110101 01100011 01100001 01110011\n" +
                    "10110010 01101001 00110101 11001010 01110001\n" +
                    "01010101 10011001 01000110 00110011 10101100",
            color = verdeTerminal.copy(alpha = 0.35f), // verde semitransparente
            fontFamily = FontFamily.Monospace,         // letra de ancho fijo, estilo código
            fontSize = 12.sp,                          // sp = tamaño de texto
            textAlign = TextAlign.Center,              // texto centrado
            modifier = Modifier
                .align(Alignment.TopCenter)            // lo colocamos arriba en el centro del Box
                .padding(top = 48.dp)                  // separación respecto al borde superior
        )

        // CAPA 3: código binario decorativo abajo
        Text(
            text = "11001010 01110001 01010101 10011001 01000110\n" +
                    "00110011 10101100 01001100 01110101 01100011\n" +
                    "01100001 01110011 10110010 01101001 00110101",
            color = verdeTerminal.copy(alpha = 0.35f),
            fontFamily = FontFamily.Monospace,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.BottomCenter)         // abajo en el centro
                .padding(bottom = 48.dp)               // separación respecto al borde inferior
        )

        // CAPA 4: el contenido de la tarjeta
        // COLUMN: apila los elementos de arriba a abajo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),                                   // margen interior de 16 dp
            horizontalAlignment = Alignment.CenterHorizontally,    // centra cada elemento en horizontal
            verticalArrangement = Arrangement.Center               // centra el bloque entero en vertical
        ) {
            // IMAGE: la foto de perfil (archivo foto_perfil en res/drawable)
            Image(
                painter = painterResource(id = R.drawable.foto_perfil), // carga la imagen de res/drawable
                contentDescription = "Foto de perfil de usuario",       // descripción para accesibilidad
                modifier = Modifier
                    .size(150.dp)                                  // tamaño de 150x150 dp
                    // Aro verde alrededor de la foto. Va ANTES de clip porque
                    // el orden de los modificadores importa.
                    .border(3.dp, verdeTerminal, CircleShape)
                    .clip(CircleShape),                            // recorta la foto en forma de círculo
                contentScale = ContentScale.Crop                   // rellena el círculo recortando lo que sobre
            )

            // Spacer = hueco vacío para separar elementos
            Spacer(modifier = Modifier.height(24.dp))

            // TEXT: nombre
            Text(
                text = "Lucas Hernandez",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold, // negrita
                color = Color.White           // blanco para que se lea sobre el fondo oscuro
            )

            // TEXT: rol con efecto máquina de escribir y cursor parpadeante
            // Usamos " " cuando el cursor está apagado para que el texto no se mueva
            Text(
                // Texto escrito hasta ahora + cursor "_" si está encendido, o un espacio si no
                text = textoVisible + if (cursorVisible) "_" else " ",
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
                color = verdeTerminal
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ROW: pone las etiquetas de tecnologías en horizontal
            // spacedBy(8.dp) deja 8 dp de hueco entre etiquetas
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // forEach repite el bloque una vez por cada elemento de la lista
                tecnologias.forEach { tecnologia ->
                    Text(
                        text = tecnologia,
                        color = verdeTerminal,
                        fontSize = 12.sp,
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            // Borde verde con esquinas muy redondeadas (forma de píldora)
                            .border(1.dp, verdeTerminal.copy(alpha = 0.6f), RoundedCornerShape(50))
                            // Espacio entre el texto y el borde
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // BUTTON: enlace a GitHub (gris)
            Button(
                // onClick: lo que pasa al pulsar el botón
                onClick = {
                    // Intent = "intención": le pedimos a Android que haga algo.
                    // ACTION_VIEW = "quiero ver esto" (una dirección web).
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/lucas196669") // convierte el texto en una URI
                    )
                    context.startActivity(intent) // lanza la acción
                },
                modifier = Modifier.fillMaxWidth(0.8f), // el botón ocupa el 80% del ancho
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3D444D), // fondo del botón
                    contentColor = Color.White          // texto del botón
                )
            ) {
                // Contenido del botón: el texto que se ve
                Text(text = "Mi Perfil de GitHub")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BUTTON: enlace a LinkedIn (azul)
            // Funciona igual que el de GitHub, cambiando la dirección y el color.
            Button(
                onClick = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.linkedin.com/in/lucas-hernandez-romero-b826b343a/")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0A66C2), // fondo del botón
                    contentColor = Color.White          // texto del botón
                )
            ) {
                Text(text = "Mi Perfil de LinkedIn")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BUTTON: enviar un correo (rojo)
            Button(
                onClick = {
                    // ACTION_SENDTO + "mailto:" = abrir la app de correo
                    // con la dirección ya puesta como destinatario.
                    val intent = Intent(
                        Intent.ACTION_SENDTO,
                        Uri.parse("mailto:lucas.hernandez.romero2007@gmail.com")
                    )
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth(0.8f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD93025), // fondo del botón
                    contentColor = Color.White          // texto del botón
                )
            ) {
                Text(text = "Escríbeme por Gmail")
            }
        }
    }
}

// ───────────── VISTA PREVIA ─────────────
// @Preview permite ver la tarjeta en Android Studio sin ejecutar la app.
// showBackground = true pinta un fondo blanco detrás de la vista previa.
// Nota: la animación de escritura solo se ve al ejecutar la app en el
// emulador o en el móvil, no siempre en la vista previa.
@Preview(showBackground = true)
@Composable
fun TarjetaPreview() {
    Reto1TarjetaPresentacionTheme {
        TarjetaPresentacion()
    }
}