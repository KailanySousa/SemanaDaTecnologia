package com.example.a18175168.semanadatecnologia

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class VisualizarFoto : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualizar_foto)


        val btnAvancar = findViewById<Button>(R.id.btn_avancar)

        val btnVoltar = findViewById<Button>(R.id.btn_voltar)

        btnAvancar.setOnClickListener {

            val abrirPost = Intent(this, Cadastro::class.java)

            startActivity(abrirPost)
        }

        btnVoltar.setOnClickListener {

            val voltarInicio = Intent(this, MainActivity::class.java)

            startActivity(voltarInicio)
        }

    }
}
