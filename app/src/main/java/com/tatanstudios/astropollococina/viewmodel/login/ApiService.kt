package com.tatanstudios.astropollococina.viewmodel.login


import com.tatanstudios.astropollococina.model.login.ModeloLogin
import com.tatanstudios.astropollococina.model.ordenes.ModeloDatosBasicos
import com.tatanstudios.astropollococina.model.ordenes.ModeloNuevasOrdenes
import com.tatanstudios.astropollococina.model.ordenes.ModeloProductoOrdenes
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    // VERIFICACION DE NUMERO
    @POST("restaurante/login")
    @FormUrlEncoded
    fun verificarUsuarioPassword(@Field("usuario") telefono: String,
                          @Field("password") password: String,
                          @Field("idfirebase") idfirebase: String?
                          ): Single<ModeloLogin>

    // LISTADO DE NUEVAS ORDENES
    @POST("restaurante/nuevas/ordenes")
    @FormUrlEncoded
    fun listadoNuevasOrdenas(@Field("id") id: String,
                             @Field("idfirebase") idfirebase: String?
    ): Single<ModeloNuevasOrdenes>


    // LISTADO DE PRODUCTOS DE UNA ORDEN
    @POST("restaurante/listado/producto/orden")
    @FormUrlEncoded
    fun listadoProductosOrden(@Field("idorden") idorden: Int
    ): Single<ModeloProductoOrdenes>

    // CANCELAR ORDEN
    @POST("restaurante/cancelar/orden")
    @FormUrlEncoded
    fun cancelarOrden(@Field("idorden") idorden: Int,
                      @Field("mensaje") mensaje: String
    ): Single<ModeloDatosBasicos>



}


