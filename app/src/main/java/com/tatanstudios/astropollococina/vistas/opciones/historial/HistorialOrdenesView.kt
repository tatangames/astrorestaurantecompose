package com.tatanstudios.astropollococina.vistas.opciones.historial

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tatanstudios.astropollococina.extras.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.navOptions
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.BarraToolbarColor
import com.tatanstudios.astropollococina.componentes.CardHistorialOrden
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ProductoItemCard
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.model.ordenes.ModeloHistorialOrdenesArray
import com.tatanstudios.astropollococina.model.rutas.Routes
import com.tatanstudios.astropollococina.network.RetrofitBuilder
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.HistorialFechasBuscarViewModel

@Composable
fun HistorialOrdenScreen(navController: NavHostController,
                         _fecha1: String, _fecha2: String,
                         viewModel: HistorialFechasBuscarViewModel = viewModel()
) {

    val ctx = LocalContext.current
    var boolDatosCargados by remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(true)
    val tokenManager = remember { TokenManager(ctx) }
    val resultado by viewModel.resultado.observeAsState()
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine
    val keyboardController = LocalSoftwareKeyboardController.current
    var _idusuario by remember { mutableStateOf("") }

    var modeloListaHistorialArray by remember { mutableStateOf(listOf<ModeloHistorialOrdenesArray>()) }


    LaunchedEffect(Unit) {
        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
            viewModel.historialListadonRetrofit(_idusuario, _fecha1, _fecha2)
        }
    }

    // ocultar teclado
    keyboardController?.hide()


    Scaffold(
        topBar = {
            BarraToolbarColor(
                navController,
                stringResource(R.string.historial),
                colorResource(R.color.colorRojo),
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            if (modeloListaHistorialArray.isEmpty() && boolDatosCargados) {
                // Mostrar imagen si la lista está vacía
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.carrovacio),
                        contentDescription = stringResource(R.string.sin_ordenes),
                        modifier = Modifier
                            .fillMaxWidth(0.4f)
                            .aspectRatio(1f),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.no_hay_ordenes),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black,
                        fontSize = 18.sp
                    )
                }

            } else {
                LazyColumn(
                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp,
                        top = 0.dp
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(modeloListaHistorialArray) { tipoOrden ->

                        CardHistorialOrden(
                            orden = tipoOrden.id.toString(),
                            fecha = tipoOrden.fechaOrden,
                            venta = tipoOrden.totalFormat,
                            estado = tipoOrden.estado,
                            haycupon = tipoOrden.haycupon,
                            cupon = tipoOrden.mensajeCupon,
                            haypremio = tipoOrden.haypremio,
                            premio = tipoOrden.textopremio,
                            cliente = tipoOrden.cliente,
                            direccion = tipoOrden.direccion,
                            telefono = tipoOrden.telefono,
                            nota = tipoOrden.notaOrden,
                            onClick = {

                              // VER PRODUCTOS

                                navController.navigate(
                                    Routes.VistaListadoProductosHistorialOrden.createRoute(
                                        idorden = tipoOrden.id.toString(),
                                    ),
                                    navOptions {
                                        launchSingleTop = true
                                    }
                                )
                            }
                        )

                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }


        }

        if (isLoading) {
            LoadingModal(isLoading = true)
        }

        resultado?.getContentIfNotHandled()?.let { result ->
            when (result.success) {
                1 -> {
                    modeloListaHistorialArray = result.lista
                    boolDatosCargados = true
                }
                else -> {
                    CustomToasty(
                        ctx,
                        stringResource(id = R.string.error_reintentar_de_nuevo),
                        ToastType.ERROR
                    )

                }
            }
        }
    } // end-scalfold


}