package com.example.a18175168.semanadatecnologia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCamera = findViewById<Button>(R.id.btn_camera)

        btnCamera.setOnClickListener {

            val abrirCamera = Intent(this, CameraActivity::class.java)

            startActivity(abrirCamera)
        }

    }


}
