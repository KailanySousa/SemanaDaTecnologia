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
import com.example.a18175168.semanadatecnologia.R.id.recycler_view_filtro


class VisualizarFoto : AppCompatActivity() {

    val cameraActivity:CameraActivity? = null
    var rotatedBitmap:Bitmap? = null

    @TargetApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_foto)

//        Picasso.with(img_cadastro.context).cancelRequest(img_cadastro)
//        Picasso.with(img_cadastro.context).load("http://54.242.6.253$").into(img_cadastro)

        val recyclerViewFiltro:RecyclerView = findViewById(R.id.recycler_view_filtro)
        recyclerViewFiltro.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String
        val cameraFront = intent.getSerializableExtra("cameraFront") as String

        val foto = Foto(pegarCaminho)

        val file  = File(pegarCaminho)


        val fotoBitmap = BitmapFactory.decodeFile(file.toString())

        if(cameraFront == ""){
            rotatedBitmap = modifyOrientation(fotoBitmap, pegarCaminho)
        }else{
            rotatedBitmap = modifyOrientation2(fotoBitmap, pegarCaminho)
        }


        val reducedBitmap = Bitmap.createScaledBitmap(rotatedBitmap,640,480,true)

//        val rotatedBitmap = modifyOrientation(fotoBitmap,pegarCaminho)

        val filtroBitmap = BitmapFactory.decodeResource(resources, R.drawable.filtros_hackathon)

//
//        val bmOverlay = Bitmap.createBitmap(fotoBitmap.getWidth(),fotoBitmap.getHeight(),fotoBitmap.getConfig())
//
//        val canvas = Canvas(bmOverlay)
//        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
//        canvas.drawBitmap(filtroBitmap, 0F, 0F, paint)

        preview_img.setImageBitmap(rotatedBitmap)



        val btnAvancar = findViewById<Button>(R.id.btn_avancar)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)


        btnAvancar.setOnClickListener {

            val bs = ByteArrayOutputStream()
            val drawable = preview_img.drawable as BitmapDrawable
            val bitmap = drawable.bitmap
            bitmap.compress(Bitmap.CompressFormat.PNG , 100,bs)

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

        carregarLista(reducedBitmap)
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

    fun modifyOrientation2(bitmap: Bitmap, image_absolute_path: String): Bitmap {


        return rotate(bitmap, 270f)


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

        val filtro1 = Filtro(R.drawable.senai1, "Semana da Tecnologia" )
        val filtro2 = Filtro(R.drawable.cafe_2, "Café RH" )
        val filtro3 = Filtro(R.drawable.e1, "Empresas 1" )
        val filtro4 = Filtro(R.drawable.e2, "Empresas 2" )
        val filtro5 = Filtro(R.drawable.e3, "Empresas 3" )
        val filtro6 = Filtro(R.drawable.e4, "Empresas 4" )
        val filtro7 = Filtro(R.drawable.e5, "Empresas 5" )
        val filtro8 = Filtro(R.drawable.e6, "Empresas 6" )
        val filtro9 = Filtro(R.drawable.e7, "Empresas 7" )
        val filtro10 = Filtro(R.drawable.e8, "Empresas 8" )
        val filtro11 = Filtro(R.drawable.e9, "Empresas 9" )
        val filtro12 = Filtro(R.drawable.feixes, "Feixes" )
        val filtro13 = Filtro(R.drawable.forum, "Forum" )
        val filtro16 = Filtro(R.drawable.hackathon, "Hackathon 1" )
        val filtro17 = Filtro(R.drawable.mecathon, "Mecathon" )
        val filtro18 = Filtro(R.drawable.mundo_senai, "Mundo Senai 1" )
        val filtro19 = Filtro(R.drawable.semana_encontro, "Semana Encontro" )
        val filtro20 = Filtro(R.drawable.senai, "Senai 1" )
        val filtro21 = Filtro(R.drawable.filtros_brasil, "Brasil" )
        val filtro22 = Filtro(R.drawable.filtros_dragao, "Dragão" )
        val filtro23 = Filtro(R.drawable.filtros_eufui, "#EUFUI" )
        val filtro24 = Filtro(R.drawable.filtros_hackathon, "Hackathon 2" )
        val filtro25 = Filtro(R.drawable.filtros_lux, "Lux" )
        val filtro26 = Filtro(R.drawable.filtros_mundosenai, "Mundo Senai 2" )
        val filtro27 = Filtro(R.drawable.filtros_senai, "Senai 2" )
        val filtro28 = Filtro(R.drawable.filtros_shrek, "Shrek" )
        val filtro29 = Filtro(R.drawable.filtros_vencontro, "V Encontro" )
        val filtro30 = Filtro(R.drawable.filtros_yasuo, "Yasuo" )

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
        filtros.add(filtro11)
        filtros.add(filtro12)
        filtros.add(filtro13)
        filtros.add(filtro16)
        filtros.add(filtro17)
        filtros.add(filtro18)
        filtros.add(filtro19)
        filtros.add(filtro20)
        filtros.add(filtro21)
        filtros.add(filtro22)
        filtros.add(filtro23)
        filtros.add(filtro24)
        filtros.add(filtro25)
        filtros.add(filtro26)
        filtros.add(filtro27)
        filtros.add(filtro28)
        filtros.add(filtro29)
        filtros.add(filtro30)

        Log.d("FILTROS", filtros.toString())

        val filtroAdapter = FiltrosAdapter(filtros, this, foto)
        recycler_view_filtro.adapter = filtroAdapter

    }



}
