package com.example.pdmlugaresturisticos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.DestinoTuristico

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbHelper = DataBaseHelper(this)


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


        // Crear dos destinos turísticos de ejemplo
        val destino1 = DestinoTuristico(
            nombre = "Playa Hermosa",
            descripcion = "Una playa con aguas cristalinas y arena blanca.",
            imagen = "http://example.com/playa_hermosa.jpg"
        )

        val destino2 = DestinoTuristico(
            nombre = "Montaña Majestuosa",
            descripcion = "Una montaña con vistas espectaculares y rutas de senderismo.",
            imagen = "http://example.com/montana_majestuosa.jpg"
        )

        // Insertar los destinos en la base de datos
        dbHelper.insertDestinoTuristico(destino1)
        dbHelper.insertDestinoTuristico(destino2)



    }
}