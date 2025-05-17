package com.tatanstudios.astropollococina.model.rutas

sealed class Routes(val route: String) {
    object VistaSplash: Routes("splash")
    object VistaLogin: Routes("login")
    object VistaPrincipal: Routes("principal")

    // AL TOCAR LA CARD DE NUEVAS ORDENES
    object VistaEstadoNuevaOrden: Routes("ordenEstadoNuevaOrden/{idorden}") {
        fun createRoute(idorden: String) = "ordenEstadoNuevaOrden/$idorden"
    }

    // Nueva orden -> cuando se selecciona un producto y se vera su info
    object VistaInfoProductoOrden: Routes("infoProductoOrden/{idproducto}") {
        fun createRoute(idproducto: String) = "infoProductoOrden/$idproducto"
    }

    // LISTADO DE ORDENES EN PREPARACION
    object VistaListadoOrdenPreparacion: Routes("listadoPreparacion")


    // Preparacion orden -> cuando se selecciona un producto y se vera su info
    object VistaEstadoPreparacionOrden: Routes("ordenEstadoPreparacionOrden/{idorden}") {
        fun createRoute(idorden: String) = "ordenEstadoPreparacionOrden/$idorden"
    }

    // LISTADO DE ORDENES COMPLETADAS
    object VistaListadoOrdenCompletadas: Routes("listadoCompletadas")

    // LISTADO DE ORDENES CANCELADAS
    object VistaListadoOrdenCanceladas: Routes("listadoCanceladas")


    // LISTADO DE PRODUCTOS
    object VistaListadoProductoOrden: Routes("listadoProductosOrden/{idorden}") {
        fun createRoute(idorden: String) = "listadoProductosOrden/$idorden"
    }


    // LISTADO DE CATEGORIAS
    object VistaListadoCategorias: Routes("listadoCategorias")


    // CUANDO SE TOCA UNA CATEGORIA Y VAMOS A VER LISTADO PRODUCTOS
    object VistaListaProductosCategorias: Routes("listadoProductosCategorias/{idcategoria}") {
        fun createRoute(idcategoria: String) = "listadoProductosCategorias/$idcategoria"
    }


    // HISTORIAL FECHA
    object VistaHistorialFecha: Routes("historialFecha")


    // HISTORIAL LISTADO ORDENES
    object VistaHistorialListadoOrden: Routes("historialListadoOrdenes/{fecha1}/{fecha2}") {
        fun createRoute(fecha1: String, fecha2: String) = "historialListadoOrdenes/$fecha1/$fecha2"
    }

    // LISTADO DE PRODUCTOS DE UNA ORDEN
    object VistaListadoProductosHistorialOrden: Routes("listadoProductosHistorialOrden/{idorden}") {
        fun createRoute(idorden: String) = "listadoProductosHistorialOrden/$idorden"
    }

    // NOTIFICACIONES
    object VistaNotificaciones: Routes("notificaciones")




}