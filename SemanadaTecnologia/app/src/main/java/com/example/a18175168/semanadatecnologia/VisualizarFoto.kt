package com.example.a18175168.semanadatecnologia

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import com.example.a18175168.semanadatecnologia.model.Foto
import kotlinx.android.synthetic.main.activity_visualizar_foto.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI
import android.graphics.Paint.FILTER_BITMAP_FLAG
import android.R.attr.bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap





class VisualizarFoto : AppCompatActivity() {

    val cameraActivity:CameraActivity? = null

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_foto)

//        Picasso.with(img_cadastro.context).cancelRequest(img_cadastro)
//        Picasso.with(img_cadastro.context).load("http://54.242.6.253$").into(img_cadastro)

        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String

        val foto = Foto(pegarCaminho)

        val file  = File(pegarCaminho)


        val fotoBitmap = BitmapFactory.decodeFile(file.toString())

        val filtroBitmap = BitmapFactory.decodeResource(resources, R.drawable.filtro)

//
//        val bmOverlay = Bitmap.createBitmap(fotoBitmap.getWidth(),fotoBitmap.getHeight(),fotoBitmap.getConfig())
//
//        val canvas = Canvas(bmOverlay)
//        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
//        canvas.drawBitmap(filtroBitmap, 0F, 0F, paint)

        val bitmap = overlayBitmap(fotoBitmap, filtroBitmap)

        val bs = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG , 50,bs)
        preview_img.setImageBitmap(bitmap)



        val btnAvancar = findViewById<Button>(R.id.btn_avancar)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)


        btnAvancar.setOnClickListener {

            val cadastro = Intent(this@VisualizarFoto, Cadastro::class.java)
            cadastro.putExtra("foto", bs.toByteArray())
            cadastro.putExtra("caminhoFoto", foto.caminho)
            startActivity(cadastro)

//            val abrirPost = Intent(this, Cadastro::class.java)
//
//            startActivity(abrirPost)
        }

        btnVoltar.setOnClickListener {

            val voltarCamera = Intent(this, CameraActivity::class.java)

            startActivity(voltarCamera)
        }

    }

    fun overlayBitmap(bitmapBackground: Bitmap, bitmapImage: Bitmap): Bitmap {

        val bitmap1Width = bitmapBackground.width
        val bitmap1Height = bitmapBackground.height
        val bitmap2Width = bitmapImage.width
        val bitmap2Height = bitmapImage.height

        val marginLeft = (bitmap1Width * 0.5 - bitmap2Width * 0.5).toFloat()
        val marginTop = (bitmap1Height * 0.5 - bitmap2Height * 0.5).toFloat()

        val overlayBitmap = Bitmap.createBitmap(bitmap1Width, bitmap1Height, bitmapBackground.config)
        val canvas = Canvas(overlayBitmap)


        canvas.drawBitmap(bitmapBackground, Matrix(), null)
        canvas.drawBitmap(bitmapImage, marginLeft, marginTop, null)

        return overlayBitmap
    }
}
