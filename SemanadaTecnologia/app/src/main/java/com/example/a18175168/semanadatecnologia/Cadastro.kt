package com.example.a18175168.semanadatecnologia

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Toast
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
import java.io.IOException

class Cadastro : AppCompatActivity() {

    var fotoService: FotosService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        fotoService = ApiConfig.getFotosService()

        val foto = intent.getByteArrayExtra("foto")

        val bitmap = BitmapFactory.decodeByteArray(foto,0,foto.size)


        val pegarCaminho = intent.getSerializableExtra("caminhoFoto") as String

        val stream = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)

        val bytes = stream.toByteArray()

        val fileOutputStream = FileOutputStream(pegarCaminho)

        fileOutputStream.write(bytes)

        Log.d("nomeArquivo", pegarCaminho)


        img_cadastro.setImageBitmap(bitmap)

        btn_enviar.setOnClickListener{

            uploadImage(txt_nome.text.toString(), pegarCaminho)

            val voltarInicio = Intent(this, MainActivity::class.java)
            startActivity(voltarInicio)
        }


    }

    fun postStories(context: Context, caminho: String){

        val imagePath = File(caminho)

        val file = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", imagePath)

        val shareIntent = Intent("com.instagram.share.ADD_TO_STORY")
        shareIntent.setDataAndType(file, "image/*")
        shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setPackage("com.instagram.android"); //Instagram App package
        startActivity(Intent.createChooser(shareIntent, "Share.."))

    }

    fun uploadImage(nomeUsuario:String, caminhoFoto:String){

        val file = File(caminhoFoto)
        val oldBitmap: Bitmap = BitmapFactory.decodeFile(file.path)
        val bitmap = modifyOrientation(oldBitmap,file.path)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30,stream)
        val image  = stream.toByteArray()
        val imageBody = RequestBody.create(MediaType.parse("image/png"), image)

        val codBody = RequestBody.create(MediaType.parse("text/plain"),nomeUsuario)
        val body = MultipartBody.Part.createFormData("image", file.name, imageBody)

        val call = fotoService!!.uploadImageCliente(body, codBody)

        call.enqueue(object : Callback<ResponseBody>{
            override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                Toast.makeText(this@Cadastro,"ERRO!!! + ${t!!.message}", Toast.LENGTH_LONG).show()
                Log.d("erro imagem", t!!.message)
            }

            override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                Log.d("imagem", response!!.message())

                postStories(this@Cadastro, caminhoFoto)
            }


        })



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

}
