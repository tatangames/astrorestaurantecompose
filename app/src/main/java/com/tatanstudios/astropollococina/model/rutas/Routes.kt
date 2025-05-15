package com.tatanstudios.astropollococina.model.rutas

sealed class Routes(val route: String) {
    object VistaSplash: Routes("splash")
    object VistaLogin: Routes("login")
    object VistaPrincipal: Routes("principal")

    // al tocar la card de nuevas ordenes
    object VistaEstadoNuevaOrden: Routes("ordenEstadoNuevaOrden/{idorden}") {
        fun createRoute(idorden: String) = "ordenEstadoNuevaOrden/$idorden"
    }

    // Nueva orden -> cuando se selecciona un producto y se vera su info
    object VistaInfoProductoOrden: Routes("infoProductoOrden/{idproducto}") {
        fun createRoute(idproducto: String) = "infoProductoOrden/$idproducto"
    }


    object VistaListadoOrdenPreparacion: Routes("listadoPreparacion")


    // Preparacion orden -> cuando se selecciona un producto y se vera su info
    object VistaEstadoPreparacionOrden: Routes("ordenEstadoPreparacionOrden/{idorden}") {
        fun createRoute(idorden: String) = "ordenEstadoPreparacionOrden/$idorden"
    }


    object VistaListadoOrdenCompletadas: Routes("listadoCompletadas")
}