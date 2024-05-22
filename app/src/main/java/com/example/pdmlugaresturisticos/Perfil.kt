package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class Perfil : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val btnHomePerfil: ImageButton = findViewById(R.id.btnHomePerfil)
        btnHomePerfil.setOnClickListener{

            val intent: Intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)

        }

    }
}