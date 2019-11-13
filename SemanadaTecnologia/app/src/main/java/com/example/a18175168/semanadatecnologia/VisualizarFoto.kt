package com.example.a18175168.semanadatecnologia

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.*
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.a18175168.semanadatecnologia.model.Foto
import kotlinx.android.synthetic.main.activity_visualizar_foto.*
import java.io.ByteArrayOutputStream
import java.io.File
import android.graphics.Bitmap
import android.media.ExifInterface
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.a18175168.semanadatecnologia.adapter.FiltrosAdapter
import com.example.a18175168.semanadatecnologia.model.Filtro
import java.io.FileOutputStream
import java.io.IOException
import android.provider.MediaStore.Images.Media.getBitmap
import android.graphics.drawable.BitmapDrawable




class VisualizarFoto : AppCompatActivity() {

    val cameraActivity:CameraActivity? = null

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_foto)

//        Picasso.with(img_cadastro.context).cancelRequest(img_cadastro)
//        Picasso.with(img_cadastro.context).load("http://54.242.6.253$").into(img_cadastro)

        val recyclerViewFiltro:RecyclerView = findViewById(R.id.recycler_view_filtro)
        recyclerViewFiltro.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String

        val foto = Foto(pegarCaminho)

        val file  = File(pegarCaminho)


        val fotoBitmap = BitmapFactory.decodeFile(file.toString())

        val rotatedBitmap = modifyOrientation(fotoBitmap, pegarCaminho)

//        val rotatedBitmap = modifyOrientation(fotoBitmap,pegarCaminho)

        val filtroBitmap = BitmapFactory.decodeResource(resources, R.drawable.filtros_hackathon)

//
//        val bmOverlay = Bitmap.createBitmap(fotoBitmap.getWidth(),fotoBitmap.getHeight(),fotoBitmap.getConfig())
//
//        val canvas = Canvas(bmOverlay)
//        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
//        canvas.drawBitmap(filtroBitmap, 0F, 0F, paint)

        preview_img.setImageBitmap(fotoBitmap)



        val btnAvancar = findViewById<Button>(R.id.btn_avancar)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)


        btnAvancar.setOnClickListener {

            val bitmapDrawable = preview_img.drawable



            val bs = ByteArrayOutputStream()
            val drawable = preview_img.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG , 50,bs)

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

        carregarLista(rotatedBitmap)
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


    @Throws(IOException::class)
    fun modifyOrientation(bitmap: Bitmap, image_absolute_path: String): Bitmap {
        val ei = ExifInterface(image_absolute_path)
        val orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> return rotate(bitmap, 90f)

            ExifInterface.ORIENTATION_ROTATE_180 -> return rotate(bitmap, 180f)

            ExifInterface.ORIENTATION_ROTATE_270 -> return rotate(bitmap, 270f)

            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> return flip(bitmap, true, false)

            ExifInterface.ORIENTATION_FLIP_VERTICAL -> return flip(bitmap, false, true)

            else -> return bitmap
        }
    }

    fun rotate(bitmap: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
        val matrix = Matrix()
        matrix.preScale((if (horizontal) -1 else 1).toFloat(), (if (vertical) -1 else 1).toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    fun carregarLista(foto:Bitmap){

        val filtros = ArrayList<Filtro>()

        val filtro1 = Filtro(R.drawable.filtros_brasil, "Brasil" )
        val filtro2 = Filtro(R.drawable.filtros_dragao, "Dragão" )
        val filtro3 = Filtro(R.drawable.filtros_eufui, "#EUFUI" )
        val filtro4 = Filtro(R.drawable.filtros_hackathon, "Hackathon" )
        val filtro5 = Filtro(R.drawable.filtros_lux, "Lux" )
        val filtro6 = Filtro(R.drawable.filtros_mundosenai, "Mundo Senai" )
        val filtro7 = Filtro(R.drawable.filtros_senai, "Senai" )
        val filtro8 = Filtro(R.drawable.filtros_shrek, "Shrek" )
        val filtro9 = Filtro(R.drawable.filtros_vencontro, "V Encontro" )
        val filtro10 = Filtro(R.drawable.filtros_yasuo, "Yasuo" )

        filtros.add(filtro1)
        filtros.add(filtro2)
        filtros.add(filtro3)
        filtros.add(filtro4)
        filtros.add(filtro5)
        filtros.add(filtro6)
        filtros.add(filtro7)
        filtros.add(filtro8)
        filtros.add(filtro9)
        filtros.add(filtro10)

        Log.d("FILTROS", filtros.toString())

        val filtroAdapter = FiltrosAdapter(filtros, this, foto)
        recycler_view_filtro.adapter = filtroAdapter

    }



}
