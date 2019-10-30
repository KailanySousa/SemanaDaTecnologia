package com.example.a18175168.semanadatecnologia

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import kotlinx.android.synthetic.main.activity_visualizar_foto.*
import java.io.File
import java.net.URI

class VisualizarFoto : AppCompatActivity() {

    val cameraActivity:CameraActivity? = null

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_foto)

//        Picasso.with(img_cadastro.context).cancelRequest(img_cadastro)
//        Picasso.with(img_cadastro.context).load("http://54.242.6.253$").into(img_cadastro)

        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String

        val file  = File(pegarCaminho)

        val bitmap = BitmapFactory.decodeFile(file.path)

        val bitmapDrawable = BitmapDrawable(resources,bitmap)

        preview_img.background = bitmapDrawable

        val fotoEditada = preview_img.drawable as BitmapDrawable




        val btnAvancar = findViewById<Button>(R.id.btn_avancar)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)


        btnAvancar.setOnClickListener {

            sendBroadcast(Intent(Intent.ACTION_MEDIA_MOUNTED),pegarCaminho)

//            val abrirPost = Intent(this, Cadastro::class.java)
//
//            startActivity(abrirPost)
        }

        btnVoltar.setOnClickListener {

            val voltarCamera = Intent(this, CameraActivity::class.java)

            startActivity(voltarCamera)
        }

    }
}
