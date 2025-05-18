package com.tatanstudios.astropollococina.vistas.principal

import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.onesignal.OneSignal
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.CustomModalCerrarSesion
import com.tatanstudios.astropollococina.componentes.DrawerBody
import com.tatanstudios.astropollococina.componentes.DrawerHeader
import com.tatanstudios.astropollococina.extras.TokenManager
import com.tatanstudios.astropollococina.extras.itemsMenu
import com.tatanstudios.astropollococina.model.rutas.Routes
import com.tatanstudios.astropollococina.viewmodel.NuevasOrdenesViewModel
import com.tatanstudios.astropollococina.vistas.login.getOneSignalUserId
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tatanstudios.astropollococina.componentes.CardNuevaOrden
import com.tatanstudios.astropollococina.componentes.CustomModal1Boton
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.model.ordenes.ModeloNuevasOrdenesArray
import kotlinx.coroutines.flow.first
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun PrincipalScreen(
    navController: NavHostController,
    viewModel: NuevasOrdenesViewModel = viewModel()
) {

    val ctx = LocalContext.current
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    var showModalCerrarSesion by remember { mutableStateOf(false) }
    var boolDatosCargados by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(true)
    val tokenManager = remember { TokenManager(ctx) }
    val resultado by viewModel.resultado.observeAsState()
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine
    val keyboardController = LocalSoftwareKeyboardController.current
    var _idusuario by remember { mutableStateOf("") }
    var _idonesignal by remember { mutableStateOf("") }

    var popUsuarioBloqueado by remember { mutableStateOf(false) }
    var modeloListaOrdenesNuevasArray by remember { mutableStateOf(listOf<ModeloNuevasOrdenesArray>()) }
    var popErrorCargar by remember { mutableStateOf(false) }


    val refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.nuevasOrdenesRetrofit(_idusuario, _idonesignal)
        }
    )

    LaunchedEffect(Unit) {
        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
            _idonesignal = getOneSignalUserId()

            viewModel.nuevasOrdenesRetrofit(_idusuario, _idonesignal)
        }
    }

    // ocultar teclado
    keyboardController?.hide()


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                DrawerBody(items = itemsMenu) { item ->
                    when (item.id) {
                        1 -> {

                            boolDatosCargados = false
                            viewModel.nuevasOrdenesRetrofit(_idusuario, _idonesignal)
                        }
                        2 -> {

                            navController.navigate(Routes.VistaListadoOrdenPreparacion.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        3 -> {

                            navController.navigate(Routes.VistaListadoOrdenCompletadas.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        4 -> {

                            navController.navigate(Routes.VistaListadoOrdenCanceladas.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        5 -> { // CATEGORIAS

                            navController.navigate(Routes.VistaListadoCategorias.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        6 -> { // NOTIFICACION
                            navController.navigate(Routes.VistaNotificaciones.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        7 -> { // HISTORIAL

                            navController.navigate(Routes.VistaHistorialFecha.route) {
                                navOptions {
                                    launchSingleTop = true
                                }
                            }
                        }
                        8 -> {
                            // cerrar sesion
                            showModalCerrarSesion = true
                        }
                    }

                    scope.launch {
                        drawerState.close()
                    }
                }

                // Spacer para empujar el contenido hacia arriba
                Spacer(modifier = Modifier.weight(1f))

                // Texto de la versión
                Text(
                    text = stringResource(R.string.version) + " " + getVersionName(ctx),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    ) {

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.nuevas_ordenes),
                            color = Color.White,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = null, tint = Color.White)
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.colorRojo),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            }
        ) { innerPadding ->


            if (modeloListaOrdenesNuevasArray.isEmpty() && boolDatosCargados) {
                // Mostrar imagen si la lista está vacía

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.carrovacio),
                            contentDescription = stringResource(R.string.sin_ordenes),
                            modifier = Modifier
                                .fillMaxWidth(0.4f)  // más pequeña aquí
                                .aspectRatio(1f),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = stringResource(R.string.no_hay_ordenes_nuevas),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }

            }else{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .pullRefresh(pullRefreshState) // si usas pull-to-refresh
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        modeloListaOrdenesNuevasArray.forEach { tipoOrden ->
                            item {
                                CardNuevaOrden(
                                    orden = tipoOrden.id.toString(),
                                    fecha = tipoOrden.fechaOrden,
                                    venta = tipoOrden.totalFormat,
                                    haycupon = tipoOrden.haycupon,
                                    cupon = tipoOrden.mensajeCupon,
                                    haypremio = tipoOrden.haypremio,
                                    premio = tipoOrden.textopremio,
                                    cliente = tipoOrden.cliente,
                                    direccion = tipoOrden.direccion,
                                    referencia = tipoOrden.referencia,
                                    telefono = tipoOrden.telefono,
                                    nota = tipoOrden.notaOrden,
                                    onClick = {
                                        navController.navigate(
                                            Routes.VistaEstadoNuevaOrden.createRoute(
                                                tipoOrden.id.toString(),
                                            ),
                                            navOptions {
                                                launchSingleTop = true
                                            }
                                        )
                                    }
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = pullRefreshState,
                        modifier = Modifier
                            .align(Alignment.TopCenter) // ✅ ahora dentro de BoxScope
                    )
                }

            }




            if (showModalCerrarSesion) {
                CustomModalCerrarSesion(showModalCerrarSesion,
                    stringResource(R.string.cerrar_sesion),
                    onDismiss = { showModalCerrarSesion = false },
                    onAccept = {
                        scope.launch {
                            // Llamamos a deletePreferences de manera segura dentro de una coroutine
                            tokenManager.deletePreferences()

                            // cerrar modal
                            showModalCerrarSesion = false

                            navigateToLogin(navController)
                        }
                    })
            }

            if (popUsuarioBloqueado) {
                CustomModal1Boton(
                    popUsuarioBloqueado,
                    stringResource(R.string.usuario_bloqueado),
                    onDismiss = {
                        scope.launch {
                            tokenManager.deletePreferences()
                            popUsuarioBloqueado = false
                            navigateToLogin(navController)
                        }
                    })
            }

            if (popErrorCargar) {
                CustomModal1Boton(
                    popErrorCargar,
                    stringResource(R.string.error_reintentar_de_nuevo),
                    onDismiss = {
                        scope.launch {
                            viewModel.nuevasOrdenesRetrofit(_idusuario, null)
                        }
                    })
            }

            if (isLoading) {
                LoadingModal(isLoading = isLoading)
            }
        }


        resultado?.getContentIfNotHandled()?.let { result ->
            when (result.success) {

                1 -> {
                    // USUARIO BLOQUEADO
                    popUsuarioBloqueado = true

                }
                2 -> {
                    // CARGA LA PANTALLA PRINCIPAL

                    modeloListaOrdenesNuevasArray = result.lista
                    boolDatosCargados = true
                }
                else -> {
                    // Error, recargar de nuevo
                    CustomToasty(
                        ctx,
                        stringResource(id = R.string.error_reintentar_de_nuevo),
                        ToastType.ERROR
                    )
                }
            }
        }





    }
}




fun getVersionName(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "N/A"
    } catch (e: PackageManager.NameNotFoundException) {
        "N/A"
    }
}


// redireccionar a vista login
private fun navigateToLogin(navController: NavHostController) {
    navController.navigate(Routes.VistaLogin.route) {
        popUpTo(Routes.VistaPrincipal.route) {
            inclusive = true // Elimina VistaPrincipal de la pila
        }
        launchSingleTop = true // Asegura que no se creen múltiples instancias de VistaLogin
    }
}

