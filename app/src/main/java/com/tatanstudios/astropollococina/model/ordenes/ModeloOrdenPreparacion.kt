package com.tatanstudios.astropollococina.model.ordenes

import com.google.gson.annotations.SerializedName


data class ModeloOrdenesPreparacion(
    @SerializedName("success") val success: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("hayordenes") val nombre: Int,
    @SerializedName("ordenes") val lista: List<ModeloOrdenesPreparacionArray>
)

data class ModeloOrdenesPreparacionArray(
    @SerializedName("id") val id: Int,
    @SerializedName("id_cliente") val idcliente: Int,
    @SerializedName("id_servicio") val idservicio: Int,
    @SerializedName("id_zona") val idzona: Int,
    @SerializedName("nota_orden") val notaOrden: String?,
    @SerializedName("total_orden") val totalOrden: String?,
    @SerializedName("totalformat") val totalFormat: String,
    @SerializedName("fecha_orden") val fechaOrden: String,
    @SerializedName("fecha_estimada") val fechaEstimada: String?,
    @SerializedName("estado_iniciada") val estadoIniciada: Int,
    @SerializedName("fecha_iniciada") val fechaIniciada: String?,
    @SerializedName("estado_preparada") val estadoPreparada: Int,
    @SerializedName("fecha_preparada") val fechaPreparada: String?,
    @SerializedName("estado_camino") val estadoEnCamino: Int,
    @SerializedName("fecha_camino") val fechaEnCamino: String?,
    @SerializedName("estado_cancelada") val estadoCancelada: Int,
    @SerializedName("fecha_cancelada") val fechaCancelada: String?,
    @SerializedName("haycupon") val haycupon: Int,
    @SerializedName("cliente") val cliente: String,
    @SerializedName("direccion") val direccion: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("referencia") val referencia: String?,
    @SerializedName("haypremio") val haypremio: Int,
    @SerializedName("textopremio") val textopremio: String?,
    @SerializedName("mensaje_cupon") val mensajeCupon: String?,
    @SerializedName("nota_cancelada") val notaCancelada: String?,
    @SerializedName("visible") val visible: Int,
)



data class ModeloOrdenesCompletadas(
    @SerializedName("success") val success: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("hayordenes") val nombre: Int,
    @SerializedName("ordenes") val lista: List<ModeloOrdenesCompletadasArray>
)

data class ModeloOrdenesCompletadasArray(
    @SerializedName("id") val id: Int,
    @SerializedName("id_cliente") val idcliente: Int,
    @SerializedName("id_servicio") val idservicio: Int,
    @SerializedName("id_zona") val idzona: Int,
    @SerializedName("nota_orden") val notaOrden: String?,
    @SerializedName("total_orden") val totalOrden: String?,
    @SerializedName("totalformat") val totalFormat: String,
    @SerializedName("fecha_orden") val fechaOrden: String,
    @SerializedName("fecha_estimada") val fechaEstimada: String?,
    @SerializedName("estado_iniciada") val estadoIniciada: Int,
    @SerializedName("fecha_iniciada") val fechaIniciada: String?,
    @SerializedName("estado_preparada") val estadoPreparada: Int,
    @SerializedName("fecha_preparada") val fechaPreparada: String?,
    @SerializedName("estado_camino") val estadoEnCamino: Int,
    @SerializedName("fecha_camino") val fechaEnCamino: String?,
    @SerializedName("estado_cancelada") val estadoCancelada: Int,
    @SerializedName("fecha_cancelada") val fechaCancelada: String?,
    @SerializedName("haycupon") val haycupon: Int,
    @SerializedName("cliente") val cliente: String,
    @SerializedName("direccion") val direccion: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("referencia") val referencia: String?,
    @SerializedName("haypremio") val haypremio: Int,
    @SerializedName("textopremio") val textopremio: String?,
    @SerializedName("mensaje_cupon") val mensajeCupon: String?,
    @SerializedName("nota_cancelada") val notaCancelada: String?,
    @SerializedName("visible") val visible: Int,
)
