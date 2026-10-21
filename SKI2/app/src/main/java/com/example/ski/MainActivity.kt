package com.example.ski

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- MODELO ----------
enum class Category(val label: String) {
    ALL("Todo"), SKIS("Esquís"), SNOWBOARDS("Snowboards"),
    CLOTHING("Ropa"), BOOTS("Botas"), ACCESSORIES("Accesorios")
}

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val price: Double,
    val category: Category,
    @DrawableRes val image: Int,
    val description: String
)

val products = listOf(
    Product(1, "Esquís All-Mountain 170", "Rossignol", 449.0, Category.SKIS, R.drawable.esqui_allmountain, "Esquís versátiles para todo tipo de nieve, ideales para nivel intermedio."),
    Product(2, "Esquís Freeride 180", "Salomon", 599.0, Category.SKIS, R.drawable.esqui_freeride, "Más anchos para nieve polvo y fuera de pista."),
    Product(3, "Snowboard Freestyle 154", "Burton", 389.0, Category.SNOWBOARDS, R.drawable.snowboard_freestyle, "Tabla flexible pensada para parque y trucos."),
    Product(4, "Snowboard All-Mountain 158", "Lib Tech", 529.0, Category.SNOWBOARDS, R.drawable.snowboard_allmountain, "Estable a alta velocidad y ligera en giros."),
    Product(5, "Chaqueta Térmica Impermeable", "The North Face", 249.0, Category.CLOTHING, R.drawable.chaqueta_termica, "Impermeable 20K, aislamiento sintético y capucha compatible con casco."),
    Product(6, "Pantalón de Nieve", "Columbia", 129.0, Category.CLOTHING, R.drawable.pantalon_nieve, "Transpirable, con refuerzos en rodillas y ventilación lateral."),
    Product(7, "Botas de Esquí Flex 100", "Atomic", 279.0, Category.BOOTS, R.drawable.botas_esqui, "Ajuste cómodo para nivel medio con forro termoconformable."),
    Product(8, "Casco con Visera", "Smith", 119.0, Category.ACCESSORIES, R.drawable.casco, "Ventilación regulable y gran protección."),
    Product(9, "Gafas de Ventisca", "Oakley", 159.0, Category.ACCESSORIES, R.drawable.gafas_ventisca, "Lente antivaho con gran contraste en días nublados."),
    Product(10, "Guantes Térmicos", "Black Diamond", 69.0, Category.ACCESSORIES, R.drawable.guantes_termicos, "Impermeables y con forro polar interior.")
)

// ---------- ESTADO DEL CARRITO ----------
class CartState {
    val items = mutableStateMapOf<Int, Int>() // productId -> cantidad

    fun add(p: Product) { items[p.id] = (items[p.id] ?: 0) + 1 }
    fun remove(p: Product) {
        val q = (items[p.id] ?: 0) - 1
        if (q <= 0) items.remove(p.id) else items[p.id] = q
    }
    fun clear() = items.clear()
    val count get() = items.values.sum()
    val total get() = items.entries.sumOf { (id, q) -> (products.first { it.id == id }.price) * q }
}

sealed interface Screen {
    data object Home : Screen
    data class Detail(val product: Product) : Screen
    data object Cart : Screen
}

fun Double.eur() = String.format(java.util.Locale.getDefault(), "%.2f €", this)

// ---------- ACTIVITY ----------
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = Color(0xFF0D47A1))) {
                SnowShopApp()
            }
        }
    }
}

@Composable
fun SnowShopApp() {
    var screen by remember { mutableStateOf<Screen>(Screen.Home) }
    val cart = remember { CartState() }

    BackHandler(enabled = screen != Screen.Home) { screen = Screen.Home }

    when (val s = screen) {
        Screen.Home -> HomeScreen(cart, onProduct = { screen = Screen.Detail(it) }, onCart = { screen = Screen.Cart })
        is Screen.Detail -> DetailScreen(s.product, cart, onBack = { screen = Screen.Home }, onCart = { screen = Screen.Cart })
        Screen.Cart -> CartScreen(cart, onBack = { screen = Screen.Home })
    }
}

// ---------- HOME ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(cart: CartState, onProduct: (Product) -> Unit, onCart: () -> Unit) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(Category.ALL) }

    val filtered = products.filter {
        (category == Category.ALL || it.category == category) &&
                (it.name.contains(query, true) || it.brand.contains(query, true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("❄️ SnowShop", fontWeight = FontWeight.Bold) },
                actions = { CartIcon(cart.count, onCart) }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                placeholder = { Text("Buscar esquís, botas, marcas…") },
                leadingIcon = { Text("🔍") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(Category.entries) { c ->
                    FilterChip(
                        selected = c == category,
                        onClick = { category = c },
                        label = { Text(c.label) }
                    )
                }
            }
            if (filtered.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay productos que coincidan", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 280.dp), // 1 columna en móvil, más columnas según el ancho disponible
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filtered) { p -> ProductCard(p, onClick = { onProduct(p) }, onAdd = { cart.add(p) }) }
                }
            }
        }
    }
}

@Composable
fun CartIcon(count: Int, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        BadgedBox(badge = { if (count > 0) Badge { Text("$count") } }) {
            Text("🛒", fontSize = 22.sp)
        }
    }
}

@Composable
fun ProductCard(p: Product, onClick: () -> Unit, onAdd: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(p.image),
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE3F2FD))
            )
            Spacer(Modifier.height(8.dp))
            Text(p.brand, fontSize = 12.sp, color = Color.Gray)
            Text(p.name, fontWeight = FontWeight.SemiBold, maxLines = 2, minLines = 2)
            Spacer(Modifier.height(4.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(p.price.eur(), fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.weight(1f))
                FilledIconButton(onClick = onAdd, modifier = Modifier.size(36.dp)) {
                    Text("+", fontSize = 20.sp)
                }
            }
        }
    }
}

// ---------- DETALLE ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(p: Product, cart: CartState, onBack: () -> Unit, onCart: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(p.name, maxLines = 1) },
                navigationIcon = { IconButton(onClick = onBack) { Text("←", fontSize = 22.sp) } },
                actions = { CartIcon(cart.count, onCart) }
            )
        },
        bottomBar = {
            Button(
                onClick = { cart.add(p) },
                modifier = Modifier.fillMaxWidth().padding(16.dp).height(52.dp)
            ) { Text("Añadir al carrito · ${p.price.eur()}") }
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Image(
                painter = painterResource(p.image),
                contentDescription = p.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFFE3F2FD))
            )
            Spacer(Modifier.height(16.dp))
            Text(p.brand, color = Color.Gray)
            Text(p.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(p.price.eur(), fontSize = 22.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(12.dp))
            Text(p.description, fontSize = 16.sp)
        }
    }
}

// ---------- CARRITO ----------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(cart: CartState, onBack: () -> Unit) {
    var ordered by remember { mutableStateOf(false) }
    val lines = cart.items.entries.map { (id, q) -> products.first { it.id == id } to q }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi carrito") },
                navigationIcon = { IconButton(onClick = onBack) { Text("←", fontSize = 22.sp) } }
            )
        }
    ) { padding ->
        Box(Modifier.padding(padding).fillMaxSize()) {
            when {
                ordered -> Column(
                    Modifier.fillMaxSize().padding(32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("✅", fontSize = 64.sp)
                    Text("¡Pedido realizado!", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Gracias por tu compra.", textAlign = TextAlign.Center)
                    Spacer(Modifier.height(16.dp))
                    Button(onClick = onBack) { Text("Seguir comprando") }
                }
                lines.isEmpty() -> Text("Tu carrito está vacío", Modifier.align(Alignment.Center), color = Color.Gray)
                else -> Column(Modifier.fillMaxSize()) {
                    LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(lines, key = { it.first.id }) { (p, q) ->
                            Card(Modifier.fillMaxWidth()) {
                                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        painter = painterResource(p.image),
                                        contentDescription = p.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(56.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(Modifier.width(12.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(p.name, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                        Text((p.price * q).eur(), color = MaterialTheme.colorScheme.primary)
                                    }
                                    IconButton(onClick = { cart.remove(p) }) { Text("−", fontSize = 22.sp) }
                                    Text("$q", fontWeight = FontWeight.Bold)
                                    IconButton(onClick = { cart.add(p) }) { Text("+", fontSize = 20.sp) }
                                }
                            }
                        }
                    }
                    HorizontalDivider()
                    Column(Modifier.padding(16.dp)) {
                        Row(Modifier.fillMaxWidth()) {
                            Text("Total", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                            Text(cart.total.eur(), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { cart.clear(); ordered = true },
                            modifier = Modifier.fillMaxWidth().height(52.dp)
                        ) { Text("Finalizar compra") }
                    }
                }
            }
        }
    }
}