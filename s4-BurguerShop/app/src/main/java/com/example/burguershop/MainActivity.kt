package com.example.burguershop

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.burguershop.ui.theme.BurguerShopTheme

//ACTIVIDAD PRINCIPAL

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)


        setContent {
            //MT: Aplica los colores y tiporafias por defecto a todo lo que hay dentro
            MaterialTheme{
                Surface(
                    //Surface:el lienzo de fondo que ocupa la pantalla
                    modifier = Modifier.fillMaxSize()
                ) {
                    //CatalogoHamburguesas(catalogoHamburguesas)
                }

            }
        }
    }
}

data class Producto(
    val nombre : String,
    val precio : String,
    val imanResId : Int
)
//Datos de prueba (harcodeados)
//De momento viven aqui mismo, en el codigo.

val catalogoHamburguesas = listOf(
    Producto(
        "Clasica con Queso",
        "6,5$",
        R.drawable.burger_clasica
    ),
    Producto(
        "Clasica con BBQ",
        "8,5$",
        R.drawable.burger_bbq
    ),
    Producto(
        "Clasica con Picante",
        "7,5$",
      R.drawable.burger_picante
),
    Producto(
    "Clasica con Lechuga",
    "10,5$",
    R.drawable.burger_vegetariana
),
    Producto(
    "Clasica con Pollo",
    "11,5$",
    R.drawable.burger_pollo
),
Producto(
    "Clasica Doble",                          
    "12,5$",
    R.drawable.burger_doble
),