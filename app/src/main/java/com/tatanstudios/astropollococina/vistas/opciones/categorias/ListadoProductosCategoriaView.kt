package com.tatanstudios.astropollococina.vistas.opciones.categorias

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.BarraToolbarColor
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.DialogActualizarProducto
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ProductoCategoriaItemCard
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.extras.TokenManager
import com.tatanstudios.astropollococina.model.ordenes.ModeloListaProductoCategoriasArray
import com.tatanstudios.astropollococina.network.RetrofitBuilder
import com.tatanstudios.astropollococina.viewmodel.ActualizarProductosViewModel
import com.tatanstudios.astropollococina.viewmodel.ListadoProductosCategoriaViewModel
import kotlinx.coroutines.launch

@Composable
fun ListadoProductosCategoriasScreen(navController: NavHostController, _idcategoria: Int,
                                     viewModelProductosCategorias: ListadoProductosCategoriaViewModel = viewModel(),
                                     viewModelActualizarProCate: ActualizarProductosViewModel = viewModel()
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    // listado de productos
    val isLoadingProductos by viewModelProductosCategorias.isLoading.observeAsState(initial = false)
    val resultadoProductos by viewModelProductosCategorias.resultado.observeAsState()
    var boolDatosCargados by remember { mutableStateOf(false) }

    var modeloListaProductosCategoriasArray by remember { mutableStateOf(listOf<ModeloListaProductoCategoriasArray>()) }

    val tokenManager = remember { TokenManager(ctx) }
    var idusuario by remember { mutableStateOf("") }

    //val keyboardController = LocalSoftwareKeyboardController.current

    var _idProducto by remember { mutableStateOf(0) }

    val isLoadingActualizar by viewModelActualizarProCate.isLoading.observeAsState(initial = false)
    val resultadoActualizar by viewModelActualizarProCate.resultado.observeAsState()


    // DIALOG PARA ACTIVAR/DESACTIVAR CATEGORIA
    var showDialogActualizarProducto by remember { mutableStateOf(false) }
    var _estadoActualProducto by remember { mutableStateOf(true) }

    // Lanzar la solicitud cuando se carga la pantalla
    LaunchedEffect(Unit) {
        scope.launch {
            idusuario = tokenManager.idUsuario.toString()
            viewModelProductosCategorias.listaProductosCategoriasRetrofit(idcategoria = _idcategoria)
        }
    }

    Scaffold(
        topBar = {
            BarraToolbarColor(
                navController,
                stringResource(R.string.productos),
                colorResource(R.color.colorRojo)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Listado dinámico
            items(modeloListaProductosCategoriasArray) { tipoProducto ->
                // Aquí colocas tu componente personalizado o vista para cada producto

                ProductoCategoriaItemCard(
                    imagenUrl = "${RetrofitBuilder.urlImagenes}${tipoProducto.imagen}",
                    titulo = tipoProducto.nombre?: "",
                    estado = tipoProducto.estado,
                    activo = tipoProducto.activo,
                    utilizaImagen = tipoProducto.utilizaImagen,
                    onClick = {
                        _idProducto = tipoProducto.id
                        showDialogActualizarProducto = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        DialogActualizarProducto(
            showDialog = showDialogActualizarProducto,
            estadoInicial = _estadoActualProducto,
            onDismiss = { showDialogActualizarProducto = false },
            onConfirm = { nuevoEstado ->
                _estadoActualProducto = nuevoEstado

                scope.launch {
                    viewModelActualizarProCate.actualizarProductoCateRetrofit(
                        idproducto = _idProducto,
                        valorcheck = if (_estadoActualProducto) 1 else 0
                    )
                }
            }
        )

        if (isLoadingProductos) {
            LoadingModal(isLoading = true)
        }

        if (isLoadingActualizar) {
            LoadingModal(isLoading = true)
        }

    } // end-scalfold


    resultadoProductos?.getContentIfNotHandled()?.let { result ->
        when (result.success) {
            1 -> {
                // LISTADO DE PRODUCTOS
                modeloListaProductosCategoriasArray = result.lista
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


    resultadoActualizar?.getContentIfNotHandled()?.let { result ->
        when (result.success) {
            1 -> {
                // ACTUALIZADO
                CustomToasty(
                    ctx,
                    stringResource(id = R.string.actualizado),
                    ToastType.INFO
                )
                scope.launch {
                    idusuario = tokenManager.idUsuario.toString()
                    viewModelProductosCategorias.listaProductosCategoriasRetrofit(idcategoria = _idcategoria)
                }
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
