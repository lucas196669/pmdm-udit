
import 'package:flutter/material.dart';


void main() {
  runApp(const BurguerShopApp());
}

//APP RAIZ

class BurguerShopApp

extends StatelessWidget
{


const BurguerShopApp
(
{super.key});

@override
Widget build(BuildContext context) {

return MaterialApp(
debugShowCheckedModeBanner: false,//quita la cinta roja
title: "Burguer Shop",

theme: ThemeData(
//color app

colorSchemeSeed: Colors.orange,
useMaterial3: true,

),
home: const PantallaCatalogo(),

);
}
}

class Producto{
final String nombre;
final String precio;
final String ImagenAsset;

Producto({
required this.nombre,
required this.precio,
required this.ImagenAsset,
});
}

final List<Producto> catalogoHamburguesas = [
Producto(
nombre: "Clasica con Queso",
precio: "7,90",
ImagenAsset: "assets/images/burguer_clasica.jpg"
),
Producto(
nombre: "BBQ Bacon",
precio: "7,90",
ImagenAsset: "assets/images/burguer_bbq.jpg"),
Producto(
nombre: "Doble Carne",
precio: "8,50",
ImagenAsset: "assets/images/burguer_doble.jpg"),
Producto(
nombre: "Vegetariana",
precio: "7,20",
ImagenAsset: "assets/images/burguer_vegetariana.jpg"),
Producto(
nombre: "Picante Jalapeño",
precio: "7,80",
ImagenAsset: "assets/images/burguer_picante.jpg"),
Producto(
nombre: "Pollo Crispy",
precio: "6,90",
ImagenAsset: "assets/images/burguer_pollo.jpg"),
];

class PantallaCatalogo extends StatelessWidget{
const PantallaCatalogo({super.key});

@override
Widget build(BuildContext context){

return Scaffold(
appBar: AppBar(title: const Text("🍔 BurguerShop")),

body: LayoutBuilder(
builder: (context, constrains){


const double anchoMinimo = 250;
const double espacio =16;
final double anchoUtil = constrains.maxWidth - (espacio * 2);
int columnas = ((anchoUtil + espacio) / (anchoMinimo + espacio)).floor();
if(columnas<1) columnas =1;

return GridView.builder(
padding: const EdgeInsets.all(espacio),
gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
crossAxisCount: columnas,
mainAxisSpacing: espacio,
crossAxisSpacing: espacio,

mainAxisExtent: 340,
),
itemCount: catalogoHamburguesas.length,
itemBuilder: (context, index){
final producto = catalogoHamburguesas[index];
return TarjetaProducto(producto:producto);
}
);

},
)

);

}

}

