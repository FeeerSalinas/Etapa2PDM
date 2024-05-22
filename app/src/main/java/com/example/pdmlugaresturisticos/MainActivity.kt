package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Haciendo llamado desde los botones a las otras pantallas
        val btnIniciarSesion: Button = findViewById(R.id.btnIniciarSesion)
        btnIniciarSesion.setOnClickListener {

            val intent: Intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)

        }

        val btnRegistrarseInicio: Button = findViewById(R.id.btnRegistrarseInicio)
        btnRegistrarseInicio.setOnClickListener {

            val intent: Intent = Intent(this, Registro::class.java)
            startActivity(intent)
        }

    }
}