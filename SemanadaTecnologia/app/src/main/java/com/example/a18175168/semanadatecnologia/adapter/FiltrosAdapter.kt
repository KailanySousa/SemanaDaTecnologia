package com.example.a18175168.semanadatecnologia.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.a18175168.semanadatecnologia.R
import com.example.a18175168.semanadatecnologia.model.Filtro
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_visualizar_foto.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.FieldPosition

class FiltrosAdapter (private val filtros:List<Filtro>, private val context:Context, private val foto:Bitmap) : RecyclerView.Adapter<FiltrosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder{
        var view = LayoutInflater.from(context).inflate(R.layout.list_view_filtro, viewGroup, false)

        return ViewHolder(view,context)
    }

    override fun getItemCount() = if (filtros != null) filtros.size else 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("TESTE", filtros.toString())
        holder.nomeFiltro.text = filtros[position].nome
        val filtroBitmap = BitmapFactory.decodeResource(context.resources, filtros[position].filto)
        holder.filtro.setImageBitmap(filtroBitmap)
        holder.itemView.setOnClickListener {

            val bitmap = overlayBitmap(foto, filtroBitmap)

            holder.foto.setImageBitmap(bitmap)

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

    class ViewHolder(itemView: View, context: Context):RecyclerView.ViewHolder(itemView) {

        val filtro: ImageView = itemView.findViewById(R.id.image_view_filtro)
        val nomeFiltro: TextView = itemView.findViewById(R.id.txt_nome_filtro)
        val foto: ImageView = (context as Activity).findViewById(R.id.preview_img)

    }
}