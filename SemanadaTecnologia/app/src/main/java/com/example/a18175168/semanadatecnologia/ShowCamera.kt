package com.example.a18175168.semanadatecnologia

import android.content.Context
import android.content.res.Configuration
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Toast
import java.io.IOException

class ShowCamera: SurfaceView, SurfaceHolder.Callback {

    var camera: Camera? = null
    var surfaceHolder: SurfaceHolder? = null

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
     }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
//        camera!!.stopPreview()
//        camera!!.release()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

        val params = camera!!.parameters

        val sizes = params.supportedPictureSizes
        var mSize:Camera.Size = sizes[0]
        for (i in 0 until sizes.size){
            mSize = sizes[i]
        }


        if (this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE){

            params.set("orientantion", "portrait")
            camera!!.setDisplayOrientation(90)
            params.setRotation(90)

        }else{
            params.set("orientantion", "landscape")
            camera!!.setDisplayOrientation(0)
            params.setRotation(0)
        }

        params.setPictureSize(640 ,480)
        camera!!.parameters = params
        try{
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    constructor(context: Context, camera: Camera) : super(context) {

        this.camera = camera
        surfaceHolder = holder
        surfaceHolder!!.addCallback(this)

    }

}