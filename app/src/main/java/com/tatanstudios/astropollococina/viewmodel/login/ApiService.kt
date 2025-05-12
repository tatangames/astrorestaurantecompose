package com.tatanstudios.astropollococina.viewmodel.login


import com.tatanstudios.astropollococina.model.login.ModeloLogin
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

    // REINTENTO SMS
    /*@POST("app/reintento/telefono")
    @FormUrlEncoded
    fun reintentoSMS(@Field("telefono") telefono: String): Single<ModeloReintentoSMS>*/




}


