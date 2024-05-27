package com.example.pdmlugaresturisticos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

class agregarSitio : AppCompatActivity() {

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){ uri ->
        if(uri != null){
            ivSitio.setImageURI(uri)
        }
        else{
            Log.i("aris", "No seleccionado")
        }
    }

    lateinit var btnImagen: Button
    lateinit var ivSitio: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_sitio)

        btnImagen = findViewById(R.id.btnImagen)
        ivSitio = findViewById(R.id.ivSitio)

        btnImagen.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }



}