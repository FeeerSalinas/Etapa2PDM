package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class PaginaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagina_inicio)

        val btnPerfilUsuario: ImageButton = findViewById(R.id.btnPerfilUsuario)
        btnPerfilUsuario.setOnClickListener{

            val intent: Intent = Intent(this, Perfil::class.java)
            startActivity(intent)

        }

    }
}