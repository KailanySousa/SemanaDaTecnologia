package com.example.a18175168.semanadatecnologia

import android.hardware.Camera
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_camera.*
import android.content.Intent
import android.graphics.*
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import java.time.LocalDateTime
import android.util.Log
import android.widget.Toast
import com.example.a18175168.semanadatecnologia.model.Foto
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import java.io.*


class CameraActivity: AppCompatActivity() {

    var showCamera: ShowCamera? = null
    var frameLayout: FrameLayout? = null
    var camera: Camera? = null
    var cameraId:Int = 0
    var cameraFront = false
    private var mPicture: Camera.PictureCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        frameLayout = findViewById(R.id.frameCamera)
        var cameraAtual = 0

        camera = Camera.open(0)

        val params = camera!!.parameters

        params.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        camera!!.parameters = params

        showCamera = ShowCamera(this, camera!!)
        frameLayout!!.addView(showCamera)


        btn_foto.setOnClickListener {

            if(camera != null){
                camera!!.takePicture(null,null, mPictureCallback())
            }

        }

        btn_trocar_camera.setOnClickListener{

            val camerasNumber = Camera.getNumberOfCameras()
            if (camerasNumber > 1) {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa

                releaseCamera()
                chooseCamera()
            } else {

            }

        }

    }


    private fun releaseCamera() {
        // stop and release camera
        if (camera != null) {
            camera!!.stopPreview()
            camera!!.setPreviewCallback(null)
            camera!!.release()
            camera = null
        }
    }


    private fun findFrontFacingCamera(): Int {

        var cameraId = -1
        // Search for the front facing camera
        val numberOfCameras = Camera.getNumberOfCameras()
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i
                cameraFront = true
                break
            }
        }
        return cameraId

    }

    private fun findBackFacingCamera(): Int {
        var cameraId = -1
        //Search for the back facing camera
        //get the number of cameras
        val numberOfCameras = Camera.getNumberOfCameras()
        //for every camera check
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i
                cameraFront = false
                break

            }

        }
        return cameraId
    }

    fun chooseCamera() {
        //if the camera preview is the front
        if (cameraFront) {
            val cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                camera = Camera.open(cameraId);
                camera!!.setDisplayOrientation(90);
                showCamera!!.refreshCamera(camera!!);
            }
        } else {
            val cameraId = findFrontFacingCamera();
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                camera = Camera.open(cameraId);
                camera!!.setDisplayOrientation(90);
                showCamera!!.refreshCamera(camera!!);
            }
        }
    }

    override fun onPause() {
        super.onPause()
        camera!!.stopPreview()
        camera!!.parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE


    }

    override fun onDestroy() {
        super.onDestroy()
        camera!!.release()

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

                    val foto = Foto(arquivo_foto.toString())

//                    val fotoBitmap =  BitmapFactory.decodeFile(arquivo_foto.toString())
//
//                    val filtroBitmap = BitmapFactory.decodeResource(resources, R.drawable.teste)
//
//                    val resultado = overlay(fotoBitmap, filtroBitmap, 100F, 50F)
//



                    val previewFoto = Intent(this@CameraActivity, VisualizarFoto::class.java)
                    previewFoto.putExtra("caminhoFoto", foto.caminho)
                    startActivity(previewFoto)


                }catch (e:FileNotFoundException){
                    e.printStackTrace()
                }catch (e: IOException){
                    e.printStackTrace()
                }
            }

        }


    }

//    private fun overlay(bmp1: Bitmap, bmp2: Bitmap, x: Float, y: Float): Bitmap {

//        return bmOverlay
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun galleryAddPic() {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(getOutPutMediaFile().toString())
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        this.sendBroadcast(mediaScanIntent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getOutPutMediaFile(): File? {

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
            Toast.makeText(this, outputFile.toString() ,Toast.LENGTH_LONG).show()
        }

    }


}