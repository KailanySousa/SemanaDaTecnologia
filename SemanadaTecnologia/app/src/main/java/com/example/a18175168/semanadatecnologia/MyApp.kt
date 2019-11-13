package com.example.a18175168.semanadatecnologia

import android.app.Application

class MyApp:Application() {

    public var isOk = false

    override fun onCreate() {
        super.onCreate()
        isOk = false
    }

}