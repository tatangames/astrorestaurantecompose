package com.tatanstudios.astropollococina.vistas.login

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.extras.TokenManager
import com.tatanstudios.astropollococina.model.rutas.Routes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.LottieConstants
import com.tatanstudios.astropollococina.vistas.opciones.categorias.ListadoCategoriasScreen
import com.tatanstudios.astropollococina.vistas.opciones.categorias.ListadoProductosCategoriasScreen
import com.tatanstudios.astropollococina.vistas.opciones.historial.HistorialFechaScreen
import com.tatanstudios.astropollococina.vistas.opciones.historial.HistorialOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.historial.ListadoProductosHistorialScreen
import com.tatanstudios.astropollococina.vistas.opciones.notificacion.NotificacionScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordencanceladas.ListadoCanceladasOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordencanceladas.ListadoProductosOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordencompletadas.ListadoCompletadasOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordenesnuevas.EstadoNuevaOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordenesnuevas.InfoProductoScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordenpreparacion.EstadoPreparacionOrdenScreen
import com.tatanstudios.astropollococina.vistas.opciones.ordenpreparacion.ListadoPreparacionOrdenScreen
import com.tatanstudios.astropollococina.vistas.principal.PrincipalScreen

class SplashApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // MODO VERTICAL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        enableEdgeToEdge()
        setContent {
            // INICIO DE APLICACION
            AppNavigation()
        }
    }
}



// *** RUTAS DE NAVEGACION ***
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.VistaSplash.route) {

        composable(Routes.VistaSplash.route) { SplashScreen(navController) }
        composable(Routes.VistaLogin.route) { LoginScreen(navController) }

        composable(Routes.VistaPrincipal.route) { PrincipalScreen(navController) }

        // Cuando se Toca la Card de nuevas ordenes
        composable(Routes.VistaEstadoNuevaOrden.route) { backStackEntry ->
            val idordenStr = backStackEntry.arguments?.getString("idorden") ?: "0"
            val idorden = idordenStr.toIntOrNull() ?: 0

            EstadoNuevaOrdenScreen(navController = navController, _idorden = idorden)
        }

        // cuando se toca un producto y se vera su informacion
        composable(Routes.VistaInfoProductoOrden.route) { backStackEntry ->
            val idproductoStr = backStackEntry.arguments?.getString("idproducto") ?: "0"
            val idproducto = idproductoStr.toIntOrNull() ?: 0

            InfoProductoScreen(navController = navController, _idproducto = idproducto)
        }

        // LISTADO DE ORDENES EN PREPARACION
        composable(Routes.VistaListadoOrdenPreparacion.route) { ListadoPreparacionOrdenScreen(navController) }

        // Cuando se Toca la Card de preparacion ordenes
        composable(Routes.VistaEstadoPreparacionOrden.route) { backStackEntry ->
            val idordenStr = backStackEntry.arguments?.getString("idorden") ?: "0"
            val idorden = idordenStr.toIntOrNull() ?: 0

            EstadoPreparacionOrdenScreen(navController = navController, _idorden = idorden)
        }

        // LISTADO DE ORDENES COMPLETADAS HOY
        composable(Routes.VistaListadoOrdenCompletadas.route) { ListadoCompletadasOrdenScreen(navController) }


        // LISTADO DE ORDENES CANCELADAS HOY
        composable(Routes.VistaListadoOrdenCanceladas.route) { ListadoCanceladasOrdenScreen(navController) }


        // LISTADO DE PRODUCTOS DE UNA ORDEN
        composable(Routes.VistaListadoProductoOrden.route) { backStackEntry ->
            val idordenStr = backStackEntry.arguments?.getString("idorden") ?: "0"
            val idorden = idordenStr.toIntOrNull() ?: 0

            ListadoProductosOrdenScreen(navController = navController, _idorden = idorden)
        }

        // LISTADO DE CATEGORIAS
        composable(Routes.VistaListadoCategorias.route) { ListadoCategoriasScreen(navController) }

        // LISTADO DE PRODUCTOS DE UNA CATEGORIA
        composable(Routes.VistaListaProductosCategorias.route) { backStackEntry ->
            val idcategoriaStr = backStackEntry.arguments?.getString("idcategoria") ?: "0"
            val idcategoria = idcategoriaStr.toIntOrNull() ?: 0

            ListadoProductosCategoriasScreen(navController = navController, _idcategoria = idcategoria)
        }


        // HISTORIAL FECHA
        composable(Routes.VistaHistorialFecha.route) { HistorialFechaScreen(navController) }


        // HISTORIAL LISTADO ORDENES
        composable(Routes.VistaHistorialListadoOrden.route) { backStackEntry ->
            val fecha1 = backStackEntry.arguments?.getString("fecha1") ?: ""
            val fecha2 = backStackEntry.arguments?.getString("fecha2") ?: ""

            HistorialOrdenScreen(navController = navController, _fecha1 = fecha1, _fecha2 = fecha2)
        }


        // LISTADO DE PRODUCTOS HISTORIAL ORDEN
        composable(Routes.VistaListadoProductosHistorialOrden.route) { backStackEntry ->
            val idordenStr = backStackEntry.arguments?.getString("idorden") ?: "0"
            val idorden = idordenStr.toIntOrNull() ?: 0

            ListadoProductosHistorialScreen(navController = navController, _idorden = idorden)
        }

        // NOTIFICACIONES
        composable(Routes.VistaNotificaciones.route) { NotificacionScreen(navController) }


    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    val ctx = LocalContext.current
    val tokenManager = remember { TokenManager(ctx) }

    // Ejecutar la migración desde SharedPreferences solo una vez
    LaunchedEffect(Unit) {
        tokenManager.migrateFromSharedPreferencesIfNeeded()
    }

    val idusuario by tokenManager.idUsuario.collectAsState(initial = "")

    // Evitar que el usuario vuelva al splash con el botón atrás
    DisposableEffect(Unit) {
        onDispose {
            navController.popBackStack(Routes.VistaSplash.route, true)
        }
    }

    // Control de la navegación tras un retraso
    LaunchedEffect(idusuario) {
        delay(2000)

        if (idusuario.isNotEmpty()) {
            navController.navigate(Routes.VistaPrincipal.route) {
                popUpTo(Routes.VistaSplash.route) { inclusive = true }
            }
        } else {
            navController.navigate(Routes.VistaLogin.route) {
                popUpTo(Routes.VistaSplash.route) { inclusive = true }
            }
        }
    }

    // Animación y diseño
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.jsoncocina))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFD84B4B)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .height(225.dp)
                .align(Alignment.Center)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(bottom = 36.dp, start = 8.dp, end = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 22.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.montserratmedium)),
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Image(
                painter = painterResource(id = R.drawable.hamburguesa),
                contentDescription = stringResource(id = R.string.logotipo),
                modifier = Modifier
                    .size(width = 120.dp, height = 100.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}