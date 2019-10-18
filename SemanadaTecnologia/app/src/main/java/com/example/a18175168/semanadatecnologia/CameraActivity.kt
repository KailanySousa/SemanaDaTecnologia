package com.example.a18175168.semanadatecnologia

import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.widget.Toast
import java.time.LocalDateTime


class CameraActivity: AppCompatActivity() {

    var showCamera: ShowCamera? = null
    var frameLayout: FrameLayout? = null
    var camera: Camera? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        frameLayout = findViewById(R.id.frameCamera)

        camera = Camera.open(0)

        val params = camera!!.parameters

        params.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        camera!!.parameters = params

        showCamera = ShowCamera(this, camera!!)
        frameLayout!!.addView(showCamera)

        var cameraAtual = 0

        btn_foto.setOnClickListener {

            if(camera != null){
                camera!!.takePicture(null,null, mPictureCallback())
            }

        }

        btn_trocar_camera.setOnClickListener{

            camera!!.stopPreview()
            camera!!.release()

//            if (cameraAtual == Camera.CameraInfo.CAMERA_FACING_BACK){
//                cameraAtual = Camera.CameraInfo.CAMERA_FACING_FRONT
//            }else{
//                cameraAtual = Camera.CameraInfo.CAMERA_FACING_BACK
//            }
//            camera = Camera.open(cameraAtual)
//            camera!!.startPreview()

        }

    }

    private fun mPictureCallback() = object : Camera.PictureCallback{
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onPictureTaken(data: ByteArray?, camera: Camera?) {

            val arquivo_foto = getOutPutMediaFile()

            if (arquivo_foto == null){
                return
            }else{
                try{
                    val fos = FileOutputStream(arquivo_foto)
                    fos.write(data)
                    fos.close()
                    galleryAddPic()
                    camera!!.startPreview()

                }catch (e:FileNotFoundException){
                    e.printStackTrace()
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(getOutPutMediaFile().toString())
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        this.sendBroadcast(mediaScanIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getOutPutMediaFile(): File? {

        val state = Environment.getExternalStorageState()
        if(state != Environment.MEDIA_MOUNTED){
            return null
        }else{
            val folder_gui = File(Environment.getExternalStorageDirectory().toString() + File.separator + "SemanaDaTecnologia")

            if (!folder_gui.exists()){
                folder_gui.mkdirs()
            }

            val outputFile = File(folder_gui, "SENAI-" + LocalDateTime.now().toString() + ".jpg")
            return outputFile
        }

    }


}