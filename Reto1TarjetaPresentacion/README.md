# Reto 1 · Tarjeta de Presentación Profesional

**Módulo:** 0489 · Programación Multimedia y Dispositivos Móviles
**Autor:** Lucas Hernandez
**Tecnología:** Kotlin + Jetpack Compose (Android nativo)
**RA vinculado:** RA1 · Tecnologías de desarrollo para dispositivos móviles

## 📱 Qué es esta app

Una tarjeta de presentación digital (estilo Linktree) que muestra una foto de perfil, un nombre, un rol profesional y tres botones de contacto: uno que enlaza al perfil de GitHub, otro al de LinkedIn y otro que abre la app de correo para escribirme.

> *Sustituye las capturas de abajo por las tuyas antes de entregar.*

<!-- ![Captura de la app](captura.png) -->

## 🎯 Objetivo del reto

Partir de un proyecto Android base y modificarlo para construir una aplicación funcional propia, aplicando los conceptos vistos en clase: estructura de un proyecto Kotlin, componentes visuales de Jetpack Compose, gestión de recursos (imágenes e icono) y control de versiones con Git.

## 🛠️ Componentes y conceptos utilizados

| Componente / concepto | Para qué se usa en esta app |
|---|---|
| `Column` | Organiza los elementos en vertical (foto, nombre, rol, botones) |
| `Image` + `clip(CircleShape)` | Muestra la foto de perfil recortada en círculo |
| `Text` | Nombre, rol profesional y texto de cada botón |
| `Spacer` | Separación entre elementos y entre los botones |
| `Button` + `Intent` (`ACTION_VIEW`) | Al pulsar, abre el navegador o la app de LinkedIn/GitHub en mi perfil |
| `Intent` (`ACTION_SENDTO`) + `mailto:` | Al pulsar, abre la app de correo con mi dirección ya puesta como destinatario |
| `LocalContext` | Permite a un Composable obtener el contexto de Android para lanzar los `Intent` |
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
- Cómo añadir varios botones a la misma pantalla reutilizando el mismo `context` y separándolos con `Spacer`.
- La diferencia entre `ACTION_VIEW` (abrir un enlace) y `ACTION_SENDTO` con `mailto:` (abrir la app de correo con el destinatario relleno).

## 🐞 Dificultades y cómo las resolví

- **Imports en rojo al pegar código:** al pegar código sin escribirlo, Android Studio no reconocía `Column`, `Image`, etc. Lo resolví usando Alt+Enter sobre cada palabra en rojo para que el IDE añadiera el import correspondiente.
- **Botones pegados entre sí:** al añadir el segundo botón, quedaban sin separación. Lo resolví metiendo un `Spacer` de 16 dp entre cada uno.
- **Enlaces de ejemplo:** al principio los botones tenían valores de prueba (`TU-USUARIO`, `TU-CORREO@gmail.com`). Los sustituí por mi usuario real de LinkedIn y mi correo, y comprobé que cada botón abría lo correcto.
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
- LinkedIn: [linkedin.com/in/TU-USUARIO](https://www.linkedin.com/in/TU-USUARIO)
- Correo: TU-CORREO@gmail.com