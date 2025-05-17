package com.tatanstudios.astropollococina.vistas.opciones.notificacion

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tatanstudios.astropollococina.R
import com.tatanstudios.astropollococina.componentes.BarraToolbarColor
import com.tatanstudios.astropollococina.viewmodel.NotificacionPruebaViewModel
import com.tatanstudios.astropollococina.vistas.login.getOneSignalUserId
import android.provider.Settings
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.tatanstudios.astropollococina.componentes.CustomToasty
import com.tatanstudios.astropollococina.componentes.LoadingModal
import com.tatanstudios.astropollococina.componentes.ToastType


@Composable
fun NotificacionScreen(
    navController: NavHostController,
    viewModel: NotificacionPruebaViewModel = viewModel()
) {
    val ctx = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

    var _idusuario by remember { mutableStateOf("") }
    var _idonesignal by remember { mutableStateOf("") }
    val isIgnoringOptimizations = remember { mutableStateOf(false) }
    val isLoading by viewModel.isLoading.observeAsState(true)
    val resultado by viewModel.resultado.observeAsState()
    val tokenManager = remember { TokenManager(ctx) }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val powerManager = ctx.getSystemService(Context.POWER_SERVICE) as PowerManager

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isIgnoringOptimizations.value = powerManager.isIgnoringBatteryOptimizations(ctx.packageName)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        isIgnoringOptimizations.value = isIgnoringBatteryOptimizations(ctx)

        scope.launch {
            _idusuario = tokenManager.idUsuario.first()
            _idonesignal = getOneSignalUserId()
        }
    }

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = stringResource(R.string.recibir_notificacion),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        viewModel.enviarNotificacionRetrofit(_idusuario, _idonesignal)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.colorVerde)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.enviar))
                }

                Spacer(modifier = Modifier.height(15.dp))

                Divider(
                    color = Color.LightGray, // Puedes personalizar el color
                    thickness = 1.dp         // O el grosor
                )

                Spacer(modifier = Modifier.height(15.dp))

                if (!isIgnoringOptimizations.value) {
                    Text(
                        text = stringResource(R.string.para_recibir_notificaciones_siempre),
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(
                        onClick = {
                            requestBatteryOptimizationPermission(ctx)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.permitir_notificacion_permantente))
                    }
                } else {
                    Text(
                        text = stringResource(R.string.optimizacion_bateria_desactivada),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }


        if (isLoading) {
            LoadingModal(isLoading = true)
        }

        resultado?.getContentIfNotHandled()?.let { result ->
            when (result.success) {
                1 -> {

                    CustomToasty(
                        ctx,
                        stringResource(id = R.string.notificacion_enviada),
                        ToastType.SUCCESS
                    )

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

fun isIgnoringBatteryOptimizations(context: Context): Boolean {
    val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
    return powerManager.isIgnoringBatteryOptimizations(context.packageName)
}

fun requestBatteryOptimizationPermission(context: Context) {
    val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
        data = Uri.parse("package:${context.packageName}")
    }
    context.startActivity(intent)
}