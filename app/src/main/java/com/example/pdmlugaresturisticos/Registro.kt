package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Registro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        //Haciendo llamado desde los botones a las otras pantallas
        val btnRegistrarseRegistro: Button = findViewById(R.id.btnRegistrarseRegistro)
        btnRegistrarseRegistro.setOnClickListener {

            val intent: Intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)

        }

        val btnVolerInicioSesion: Button = findViewById(R.id.btnVolverInicioSesion)
        btnVolerInicioSesion.setOnClickListener {

            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

    }
}