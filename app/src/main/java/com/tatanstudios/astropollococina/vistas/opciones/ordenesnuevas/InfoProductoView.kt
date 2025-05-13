package com.tatanstudios.astropollococina.vistas.opciones.ordenesnuevas

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.CancelarOrdenViewModel
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.IniciarOrdenViewModel
import com.tatanstudios.astropollococina.viewmodel.ordenesnuevas.ProductosOrdenViewModel

@Composable
fun InfoProductoScreen(navController: NavHostController, _idorden: Int,
                           viewModel: InfoProductoViewModel = viewModel(),
) {
    val ctx = LocalContext.current





}