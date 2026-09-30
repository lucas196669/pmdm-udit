# Reto 1 · Tarjeta de Presentación Profesional

**Módulo:** 0489 · Programación Multimedia y Dispositivos Móviles
**Autor:** Lucas Hernandez
**Tecnología:** Kotlin + Jetpack Compose (Android nativo)
**RA vinculado:** RA1 · Tecnologías de desarrollo para dispositivos móviles

## 📱 Qué es esta app

Una tarjeta de presentación digital (estilo Linktree) con temática informática. Muestra una foto de perfil, mi nombre, mi rol profesional y tres botones de contacto, cada uno de un color: GitHub (gris), LinkedIn (azul) y correo (rojo). El fondo es un degradado oscuro con una cuadrícula verde tipo circuito y código binario decorativo, todo dibujado con código, sin imágenes externas.

> *Sustituye las capturas de abajo por las tuyas antes de entregar.*

<!-- ![Captura de la app](captura.png) -->

## 🎯 Objetivo del reto

Partir de un proyecto Android base y modificarlo para construir una aplicación funcional propia, aplicando los conceptos vistos en clase: estructura de un proyecto Kotlin, componentes visuales de Jetpack Compose, gestión de recursos (imágenes e icono) y control de versiones con Git.

## 🛠️ Componentes y conceptos utilizados

| Componente / concepto | Para qué se usa en esta app |
|---|---|
| `Box` | Apila capas una encima de otra: fondo, decoración y contenido |
| `Column` | Organiza los elementos en vertical (foto, nombre, rol, botones) |
| `Canvas` + `drawLine` | Dibuja la cuadrícula tipo circuito del fondo |
| `Brush.verticalGradient` | Crea el degradado de negro a azul noche |
| `Image` + `clip(CircleShape)` | Muestra la foto de perfil recortada en círculo |
| `Text` (con `FontFamily.Monospace`) | Nombre, rol, texto de los botones y código binario decorativo |
| `Spacer` | Separación entre elementos y entre los botones |
| `Button` + `ButtonDefaults.buttonColors` | Botones con color de fondo (`containerColor`) y de texto (`contentColor`) propios |
| `Intent` (`ACTION_VIEW`) + `Uri` | Abre el navegador o la app correspondiente en mi perfil de GitHub o LinkedIn |
| `Intent` (`ACTION_SENDTO`) + `mailto:` | Abre la app de correo con mi dirección ya puesta como destinatario |
| `LocalContext` | Permite a un Composable obtener el contexto de Android para lanzar los `Intent` |
| `Color(0xFF...)` | Define colores personalizados en hexadecimal |
| `res/drawable` | Carpeta donde vive la imagen de perfil |
| `res/mipmap` (Image Asset Studio) | Icono personalizado de la app, sustituyendo al robot de Android por defecto |
| `strings.xml` (`app_name`) | Nombre visible de la app bajo el icono, en el móvil |

## 🚀 Cómo ejecutar el proyecto

1. Clonar o abrir el proyecto en Android Studio.
2. Esperar a que sincronice Gradle.
3. Ejecutar (▶) sobre un emulador o un dispositivo Android real con la depuración USB activada.

## 🧠 Qué he aprendido

- Cómo se estructura un proyecto Android/Kotlin con Jetpack Compose.
- Cómo importar y organizar imágenes en `res/drawable`.
- Cómo usar `Column`, `Image`, `Text`, `Spacer` y `Button` para maquetar una pantalla.
- Cómo cambiar el icono de la app con Image Asset Studio (capa de fondo y capa de primer plano).
- Cómo cambiar el nombre visible de la app en `strings.xml`, sin tocar el nombre del proyecto.
- Cómo lanzar una URL externa desde un botón usando `Intent` + `Uri`.
- La diferencia entre `ACTION_VIEW` (abrir un enlace) y `ACTION_SENDTO` con `mailto:` (abrir la app de correo con el destinatario relleno).
- Cómo personalizar el color de un botón con `ButtonDefaults.buttonColors`, y cómo escribir colores en hexadecimal (`0xFF` + 6 dígitos).
- Cómo superponer capas con `Box` para poner un fondo detrás del contenido.
- Cómo dibujar formas directamente con `Canvas` (la cuadrícula del fondo) y cómo hacer degradados con `Brush`.
- Que hay que adaptar los colores del texto al fondo: sobre un fondo oscuro, el texto por defecto no se leía.

## 🐞 Dificultades y cómo las resolví

- **Imports en rojo al pegar código:** al pegar código sin escribirlo, Android Studio no reconocía `Column`, `Image`, `Color`, `ButtonDefaults`, etc. Lo resolví usando Alt+Enter sobre cada palabra en rojo para que el IDE añadiera el import correspondiente.
- **Error al cambiar el color del botón:** escribí `Color()` vacío y olvidé la coma entre `containerColor` y `contentColor`. Lo resolví dándole un valor hexadecimal al color (`Color(0xFF0A66C2)`) y separando los parámetros con comas.
- **Botones pegados entre sí:** al añadir más botones quedaban sin separación. Lo resolví metiendo un `Spacer` de 16 dp entre cada uno.
- **Texto que no se veía sobre el fondo oscuro:** al poner el fondo informático, los colores por defecto se perdían. Cambié el nombre a blanco, el rol a verde tipo terminal y aclaré el gris del botón de GitHub.
- **El botón de correo no fuerza Gmail:** `mailto:` abre la app de correo predeterminada del móvil, no una concreta. Lo entendí al probarlo: si hay varias apps de correo, Android deja elegir.

## 📂 Estructura del proyecto

```
app/src/main/java/.../MainActivity.kt   → pantalla principal (Compose)
app/src/main/res/drawable/              → imagen de perfil
app/src/main/res/mipmap-*/              → icono de la app
app/src/main/res/values/strings.xml     → nombre visible de la app
```

## 🔗 Enlaces

- GitHub: [github.com/lucas196669](https://github.com/lucas196669)
- LinkedIn: [Lucas Hernandez Romero](https://www.linkedin.com/in/lucas-hernandez-romero-b826b343a/)
- Correo: lucas.hernandez.romero2007@gmail.com