package com.tatanstudios.astropollococina.vistas.opciones.ordenesnuevas

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.permissions.isGranted
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.BarraToolbarColor
import com.tatanstudios.astropollococina.componentes.BloqueTextFieldLogin
import com.tatanstudios.astropollococina.componentes.BloqueTextFieldPassword
import com.tatanstudios.astropollococina.componentes.CustomModal1Boton
import com.tatanstudios.astropollococina.componentes.CustomModal2Botones
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ToastType
import com.tatanstudios.astropollococina.extras.TokenManager
import com.tatanstudios.astropollococina.model.rutas.Routes
import com.tatanstudios.astropollococina.viewmodel.login.LoginViewModel
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.CancelarOrdenViewModel
import kotlinx.coroutines.launch

@Composable
fun EstadoNuevaOrdenScreen(navController: NavHostController, _idorden: Int,
                           viewModelCancelar: CancelarOrdenViewModel = viewModel()) {

    val ctx = LocalContext.current
    val scope = rememberCoroutineScope()

    // PAra datos de orden cancelada
    val isLoadingCancelar by viewModelCancelar.isLoading.observeAsState(initial = false)
    val resultadoCancelar by viewModelCancelar.resultado.observeAsState()

    // para datos de iniciar orden


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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            // Texto centrado arriba
            Text(
                text = "Orden #: $_idorden",
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones justo debajo del texto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Botón Cancelar
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

                // Botón Iniciar
                Button(
                    onClick = { /* Acción iniciar */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50), // Verde
                        contentColor = Color.White
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(stringResource(R.string.iniciar))
                }
            }






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


            if (isLoadingCancelar) {
                LoadingModal(isLoading = true)
            }



        } // end-column
    } // end-sscalfold

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

}

