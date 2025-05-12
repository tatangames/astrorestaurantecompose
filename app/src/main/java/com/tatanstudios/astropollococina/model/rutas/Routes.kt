package com.tatanstudios.astropollococina.model.rutas

sealed class Routes(val route: String) {
    object VistaSplash: Routes("splash")
    object VistaLogin: Routes("login")
    object VistaPrincipal: Routes("principal")


    object VistaOrdenPreparacion: Routes("orden_preparacion")



}