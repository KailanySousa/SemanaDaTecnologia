package com.example.a18175168.semanadatecnologia.services


class ApiConfig {

    companion object {
        val API_URL = "http://35.153.181.207:3005/"

        fun getFotosService(): FotosService? {
            return RetrofitClient.getfoto(API_URL)!!.create(FotosService::class.java)
        }

    }

}