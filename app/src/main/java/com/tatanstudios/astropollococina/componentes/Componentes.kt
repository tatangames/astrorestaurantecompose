package com.tatanstudios.astropollococina.componentes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tatanstudios.astropollococina.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraToolbarColor(navController: NavController, titulo: String, backgroundColor: Color) {

    var isNavigating by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = titulo,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.White,
                fontWeight = FontWeight.Medium,
            )
        },

        navigationIcon = {
            IconButton(
                onClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        navController.popBackStack()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.atras),
                    tint = Color.White // Color del ícono de navegación
                )
            }
        },
        actions = {
            // Puedes agregar acciones adicionales aquí si lo necesitas
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor, // Color de fondo de la barra
            navigationIconContentColor = Color.White, // Color del ícono de navegación
            titleContentColor = Color.White, // Color del título
            actionIconContentColor = Color.White // Color de las acciones
        ),
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeightIn(min = 56.dp) // Define una altura mínima
    )
}



@Composable
fun DialogActualizarCategoria(
    showDialog: Boolean,
    estadoInicial: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Boolean) -> Unit
) {
    if (showDialog) {
        var estado by remember(key1 = estadoInicial) {
            mutableStateOf(estadoInicial)
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(stringResource(R.string.actualizar_categoria))
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(min = 300.dp)
                ) {
                    Column {
                        Text(stringResource(R.string.estado_de_categoria))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Switch(
                                checked = estado,
                                onCheckedChange = { estado = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = colorResource(R.color.colorAzul), // Azul
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (estado) stringResource(R.string.activado) else stringResource(R.string.desactivado))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(estado)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.guardar))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancelar), color = colorResource(R.color.colorNegro))
                }
            }
        )
    }
}





@Composable
fun DialogActualizarProducto(
    showDialog: Boolean,
    estadoInicial: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (Boolean) -> Unit
) {
    if (showDialog) {
        var estado by remember(key1 = estadoInicial) {
            mutableStateOf(estadoInicial)
        }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(stringResource(R.string.actualizar_producto))
            },
            text = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(min = 300.dp)
                ) {
                    Column {
                        Text(stringResource(R.string.estado_de_producto))
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Switch(
                                checked = estado,
                                onCheckedChange = { estado = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = Color.White,
                                    checkedTrackColor = colorResource(R.color.colorAzul), // Azul
                                    uncheckedThumbColor = Color.White,
                                    uncheckedTrackColor = Color.LightGray
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(if (estado) stringResource(R.string.activado) else stringResource(R.string.desactivado))
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onConfirm(estado)
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(stringResource(R.string.guardar))
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(R.string.cancelar), color = colorResource(R.color.colorNegro))
                }
            }
        )
    }
}