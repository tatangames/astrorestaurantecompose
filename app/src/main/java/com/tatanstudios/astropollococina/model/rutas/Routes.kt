package com.tatanstudios.astropollococina.model.rutas

sealed class Routes(val route: String) {
    object VistaSplash: Routes("splash")
    object VistaLogin: Routes("login")
    object VistaPrincipal: Routes("principal")

    // al tocar la card de nuevas ordenes
    object VistaEstadoNuevaOrden: Routes("ordenEstadoNuevaOrden/{idorden}") {
        fun createRoute(idorden: String) = "ordenEstadoNuevaOrden/$idorden"
    }


    object VistaOrdenPreparacion: Routes("orden_preparacion")



}