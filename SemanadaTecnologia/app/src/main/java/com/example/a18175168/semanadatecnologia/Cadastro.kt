package com.example.a18175168.semanadatecnologia

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.example.a18175168.semanadatecnologia.model.Cliente
import com.example.a18175168.semanadatecnologia.model.Foto
import com.example.a18175168.semanadatecnologia.services.ApiConfig
import com.example.a18175168.semanadatecnologia.services.FotosService
import kotlinx.android.synthetic.main.activity_cadastro.*
import retrofit2.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class Cadastro : AppCompatActivity() {

    var fotoService: FotosService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        fotoService = ApiConfig.getFotosService()

        val foto = intent.getByteArrayExtra("foto")

        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String

        val bitmap = BitmapFactory.decodeByteArray(foto,0,foto.size)


        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val bytes = stream.toByteArray()

        val fileOutputStream = FileOutputStream(pegarCaminho)

        fileOutputStream.write(bytes)



        Log.d("nomeArquivo", pegarCaminho)


        img_cadastro.setImageBitmap(bitmap)

        btn_enviar.setOnClickListener{

            uploadImage(txt_nome.text.toString(), pegarCaminho)
        }


    }

    fun uploadImage(nomeUsuario:String, caminhoFoto:String){

        val file = File(caminhoFoto)
        val imageBody = RequestBody.create(MediaType.parse("image/*"), file)

        val codBody = RequestBody.create(MediaType.parse("text/plain"),nomeUsuario)
        val body = MultipartBody.Part.createFormData("image", file.name, imageBody)

        val call = fotoService!!.uploadImageCliente(body, codBody)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Toast.makeText(this@Cadastro,"ERRO!!! + ${t!!.message}", Toast.LENGTH_LONG).show()
                Log.d("erro imagem", t!!.message)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Toast.makeText(this@Cadastro,"IMAGEM ENVIADA", Toast.LENGTH_LONG).show()
                Log.d("imagem", response!!.message())
            }





        })


    }

}
