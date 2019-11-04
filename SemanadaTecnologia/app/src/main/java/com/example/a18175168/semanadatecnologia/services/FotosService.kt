package com.example.a18175168.semanadatecnologia.services

import com.example.a18175168.semanadatecnologia.model.Cliente
import com.example.a18175168.semanadatecnologia.model.Foto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface FotosService {

    @Multipart
    @POST("new-image")
    fun uploadImageCliente(@Part image: MultipartBody.Part, @Part("nome") nome: RequestBody): Call<ResponseBody>

}