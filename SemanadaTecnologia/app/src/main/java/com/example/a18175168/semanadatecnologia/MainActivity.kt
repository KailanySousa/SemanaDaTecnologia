package com.example.a18175168.semanadatecnologia

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCamera = findViewById<Button>(R.id.btn_camera)
        val permission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.CAMERA)
        requestPermissions(permission, 0)
        btnCamera.setOnClickListener {
            try {
                val abrirCamera = Intent(this, CameraActivity::class.java)

                startActivity(abrirCamera)
            }catch (e:RuntimeException){
                e.printStackTrace()

            }

        }

    }


}
