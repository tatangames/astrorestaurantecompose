package com.tatanstudios.astropollococina.vistas.opciones.ordenesnuevas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.BarraToolbarColor
import com.tatanstudios.astropollococina.componentes.CustomModal1BotonTitulo
import com.tatanstudios.astropollococina.componentes.CustomModal2Botones
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ProductoItemCard
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.extras.TokenManager
import com.tatanstudios.astropollococina.model.ordenes.ModeloProductoOrdenesArray
import com.tatanstudios.astropollococina.network.RetrofitBuilder
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.CancelarOrdenViewModel
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.IniciarOrdenViewModel
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.ProductosOrdenViewModel
import kotlinx.coroutines.launch

@Composable
fun EstadoNuevaOrdenScreen(navController: NavHostController, _idorden: Int,
                           viewModelCancelar: CancelarOrdenViewModel = viewModel(),
                           viewModelIniciarOrden: IniciarOrdenViewModel = viewModel(),
                           viewModelProductosOrden: ProductosOrdenViewModel = viewModel(),
) {
    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    // listado de productos
    val isLoadingProductos by viewModelProductosOrden.isLoading.observeAsState(initial = false)
    val resultadoProductos by viewModelProductosOrden.resultado.observeAsState()
    var boolDatosCargados by remember { mutableStateOf(false) }

    // Para datos de orden cancelada
    val isLoadingCancelar by viewModelCancelar.isLoading.observeAsState(initial = false)
    val resultadoCancelar by viewModelCancelar.resultado.observeAsState()

    // para datos de iniciar orden
    val isLoadingIniciar by viewModelIniciarOrden.isLoading.observeAsState(initial = false)
    val resultadoIniciar by viewModelIniciarOrden.resultado.observeAsState()
    var showDialogIniciarOrden by remember { mutableStateOf(false) }
    var showDialogOrdenIniciadaApi by remember { mutableStateOf(false) }


    // titulo y mensaje de respuestas
    var textoTituloApi by remember { mutableStateOf("") }
    var textoMensajeApi by remember { mutableStateOf("") }


    var modeloListaProductosArray by remember { mutableStateOf(listOf<ModeloProductoOrdenesArray>()) }


    val tokenManager = remember { TokenManager(ctx) }
    var idusuario by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    var showDialogCancelamiento by remember { mutableStateOf(false) }
    var inputTextCancelar by remember { mutableStateOf("") }

    var showModal2BotonCancelamiento by remember { mutableStateOf(false) }
    val textoNotaCancelaEsRequerida = stringResource(R.string.nota_cancelamiento_es_requerida)


    // Lanzar la solicitud cuando se carga la pantalla
    LaunchedEffect(Unit) {
        scope.launch {
            idusuario = tokenManager.idUsuario.toString()
            viewModelProductosOrden.productosOrdenRetrofit(idorden = _idorden)
        }
    }



    Scaffold(
        topBar = {
            BarraToolbarColor(
                navController,
                stringResource(R.string.orden_en_preparacion),
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
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Orden #: $_idorden",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { showDialogCancelamiento = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.cancelar))
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick = { showDialogIniciarOrden = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(stringResource(R.string.iniciar))
                    }
                }
            }

            // Listado dinámico
            items(modeloListaProductosArray) { tipoProducto ->
                // Aquí colocas tu componente personalizado o vista para cada producto


                ProductoItemCard(
                    cantidad = tipoProducto.cantidad.toString(),
                    hayImagen = tipoProducto.utilizaImagen,
                    imagenUrl = "${RetrofitBuilder.urlImagenes}${tipoProducto.imagen}",
                    titulo = tipoProducto.nombreProducto,
                    descripcion = tipoProducto.nota,
                    precio = tipoProducto.precio,
                    onClick = {


                    }
                )




            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }



        // INPUT TEXT NOTA CANCELAMIENTO
        if (showDialogCancelamiento) {
            AlertDialog(
                onDismissRequest = { showDialogCancelamiento = false },
                title = { Text(stringResource(R.string.cancelacion)) },
                text = {
                    Column {
                        Text(stringResource(R.string.nota_para_el_cliente))
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = inputTextCancelar,
                            onValueChange = {
                                if (it.length <= 300) inputTextCancelar = it
                            },
                            placeholder = { Text(stringResource(R.string.motivo)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {

                            if(inputTextCancelar.isEmpty()) {
                                CustomToasty(
                                    ctx,
                                    textoNotaCancelaEsRequerida,
                                    ToastType.INFO
                                )
                            }else{
                                showDialogCancelamiento = false
                                showModal2BotonCancelamiento = true
                            }

                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                    ) {
                        Text(stringResource(R.string.enviar))
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {

                            showDialogCancelamiento = false
                        },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color.Black)
                    ) {
                        Text(stringResource(R.string.cancelar))
                    }
                }
            )
        }

        // CONFIRMAR PARA CANCELAR
        if(showModal2BotonCancelamiento){
            CustomModal2Botones(
                showDialog = true,
                message = stringResource(R.string.cancelar_orden),
                onDismiss = { showModal2BotonCancelamiento = false },
                onAccept = {
                    showModal2BotonCancelamiento = false

                    coroutineScope.launch {
                        viewModelCancelar.cancelarOrdenRetrofit(
                            idorden = _idorden,
                            notaCancelar = inputTextCancelar
                        )
                    }

                },
                stringResource(R.string.si),
                stringResource(R.string.no),
            )
        }

        // CONFIRMAR PARA INICIAR ORDEN
        if(showDialogIniciarOrden){
            CustomModal2Botones(
                showDialog = true,
                message = stringResource(R.string.iniciar_orden),
                onDismiss = { showDialogIniciarOrden = false },
                onAccept = {
                    showDialogIniciarOrden = false
                    textoTituloApi = ""
                    textoMensajeApi = ""

                    coroutineScope.launch {
                        viewModelIniciarOrden.iniciarOrdenRetrofit(
                            idorden = _idorden,
                        )
                    }

                },
                stringResource(R.string.si),
                stringResource(R.string.no),
            )
        }

        // MENSAJE DE API AL INICIAR ORDEN
        if(showDialogOrdenIniciadaApi){

            CustomModal1BotonTitulo(
                showDialog = showDialogOrdenIniciadaApi,
                title = textoTituloApi,
                message = textoMensajeApi,
                onDismiss = {
                    showDialogOrdenIniciadaApi = false
                    navController.popBackStack()
                }
            )
        }

        if (isLoadingProductos) {
            LoadingModal(isLoading = true)
        }

        if (isLoadingCancelar) {
            LoadingModal(isLoading = true)
        }

        if (isLoadingIniciar) {
            LoadingModal(isLoading = true)
        }



    } // end-scalfold








    resultadoProductos?.getContentIfNotHandled()?.let { result ->
        when (result.success) {
            1 -> {
                // LISTADO DE PRODUCTOS
                modeloListaProductosArray = result.lista
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


    resultadoCancelar?.getContentIfNotHandled()?.let { result ->
        when (result.success) {
            1 -> {
                // ORDEN CANCELADA
                CustomToasty(
                    ctx,
                    stringResource(id = R.string.orden_cancelada),
                    ToastType.SUCCESS
                )
                navController.popBackStack()
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


    resultadoIniciar?.getContentIfNotHandled()?.let { result ->
        when (result.success) {
            1 -> {
                // ORDEN CANCELADA POR CLIENTE
                textoTituloApi = result.titulo?: ""
                textoMensajeApi = result.mensaje?: ""
                showDialogOrdenIniciadaApi = true
            }
            2 -> {
               // ORDEN INICIADA
                textoTituloApi = result.titulo?: ""
                textoMensajeApi = result.mensaje?: ""
                showDialogOrdenIniciadaApi = true
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

