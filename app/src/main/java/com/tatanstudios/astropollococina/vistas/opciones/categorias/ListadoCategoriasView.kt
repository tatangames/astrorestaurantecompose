package com.tatanstudios.astropollococina.vistas.opciones.categorias

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.tatanstudios.astropollococina.componentes.CardCategorias
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.DialogActualizarCategoria
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.model.ordenes.ModeloCategoriasArray
import com.tatanstudios.astropollococina.model.rutas.Routes
import com.tatanstudios.astropollococina.network.RetrofitBuilder
import com.tatanstudios.astropollococina.viewmodel.ActualizarCategoriaViewModel
import com.tatanstudios.astropollococina.viewmodel.CategoriasBuscarViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListadoCategoriasScreen(navController: NavHostController,
                            viewModel: CategoriasBuscarViewModel = viewModel(),
                            viewModelActualizar: ActualizarCategoriaViewModel = viewModel(),
) {

    val ctx = LocalContext.current
    var boolDatosCargados by remember { mutableStateOf(false) }

    val isLoading by viewModel.isLoading.observeAsState(true)
    val resultado by viewModel.resultado.observeAsState()

    val isLoadingActualizar by viewModelActualizar.isLoading.observeAsState(true)
    val resultadoActualizar by viewModelActualizar.resultado.observeAsState()


    val tokenManager = remember { TokenManager(ctx) }
    val scope = rememberCoroutineScope() // Crea el alcance de coroutine
    val keyboardController = LocalSoftwareKeyboardController.current
    var _idusuario by remember { mutableStateOf("") }

    var modeloListaCategoriasArray by remember { mutableStateOf(listOf<ModeloCategoriasArray>()) }

    // DIALOG PARA OPCIONES
    val sheetStateOpciones = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheetOpciones by remember { mutableStateOf(false) }

    // DIALOG PARA ACTIVAR/DESACTIVAR CATEGORIA
    var showDialogActualizarCategoria by remember { mutableStateOf(false) }

    var _idCategoria by remember { mutableStateOf(0) }
    var _estadoActualCategoria by remember { mutableStateOf(true) }


    val refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            viewModel.categoriasRetrofit(_idusuario)
        }
    )

    LaunchedEffect(Unit) {
        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
            viewModel.categoriasRetrofit(_idusuario)
        }
    }

    // ocultar teclado
    keyboardController?.hide()


    Scaffold(
        topBar = {
            BarraToolbarColor(
                navController,
                stringResource(R.string.categorias),
                colorResource(R.color.colorRojo),
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState) // 🔄 Aquí va el pull refresh
        ) {

            if (modeloListaCategoriasArray.isEmpty() && boolDatosCargados) {
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
                    items(modeloListaCategoriasArray) { tipoCategoria ->

                        CardCategorias(
                            imagenUrl =  "${RetrofitBuilder.urlImagenes}${tipoCategoria.imagen}",
                            titulo = tipoCategoria.nombre,
                            estado = tipoCategoria.estado,
                            horario = tipoCategoria.horario,
                            usahorario = tipoCategoria.usaHorario,
                            activo = tipoCategoria.activo,
                            onClick = {
                                // Acción al tocar el Card, como navegar o mostrar detalles
                                _idCategoria = tipoCategoria.id
                                _estadoActualCategoria = tipoCategoria.activo == 1
                                showSheetOpciones = true
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }

            if (showSheetOpciones) {
                ModalBottomSheet(
                    onDismissRequest = { showSheetOpciones = false },
                    sheetState = sheetStateOpciones,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.opciones),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Botón rojo - Modificar categoría
                        Button(
                            onClick = {
                                // Acción para modificar actualizar estado categoría
                                showSheetOpciones = false
                                showDialogActualizarCategoria = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorRojo),
                                contentColor = colorResource(R.color.colorBlanco)
                            ),
                            shape = RoundedCornerShape(12.dp) // esquinas redondeadas
                        ) {
                            Text(
                                text = stringResource(R.string.modificar_categorias),
                                fontSize = 16.sp
                            )
                        }

                        // Botón azul - Producto
                        Button(
                            onClick = {
                                showSheetOpciones = false
                                // Acción para ir a productos

                                navController.navigate(
                                    Routes.VistaListaProductosCategorias.createRoute(
                                        _idCategoria.toString(),
                                    ),
                                    navOptions {
                                        launchSingleTop = true
                                    }
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(R.color.colorAzul),
                                contentColor = colorResource(R.color.colorBlanco),
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.productos),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }


            DialogActualizarCategoria(
                showDialog = showDialogActualizarCategoria,
                estadoInicial = _estadoActualCategoria,
                onDismiss = { showDialogActualizarCategoria = false },
                onConfirm = { nuevoEstado ->
                    _estadoActualCategoria = nuevoEstado

                    scope.launch {
                        viewModelActualizar.actualizarCategoriasRetrofit(
                            idcategoria = _idCategoria,
                            valorcheck = if (_estadoActualCategoria) 1 else 0
                        )
                    }
                }
            )


            // 🔽 Indicador visual de pull-to-refresh
            PullRefreshIndicator(
                refreshing = refreshing,
                state = pullRefreshState,
                modifier = Modifier
                    .align(Alignment.TopCenter) // ✅ ahora dentro de BoxScope
            )
        }

        if (isLoading) {
            LoadingModal(isLoading = true)
        }

        if (isLoadingActualizar) {
            LoadingModal(isLoading = true)
        }



        resultado?.getContentIfNotHandled()?.let { result ->
            when (result.success) {
                1 -> {
                    modeloListaCategoriasArray = result.lista
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

        resultadoActualizar?.getContentIfNotHandled()?.let { result ->
            when (result.success) {
                1 -> {
                    CustomToasty(
                        ctx,
                        stringResource(id = R.string.actualizado),
                        ToastType.INFO
                    )

                    scope.launch {
                        _idusuario = tokenManager.idUsuario.first()
                        viewModel.categoriasRetrofit(_idusuario)
                    }
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