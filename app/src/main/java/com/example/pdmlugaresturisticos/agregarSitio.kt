package com.example.pdmlugaresturisticos

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.DestinoTuristico

class agregarSitio : AppCompatActivity() {

    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            ivSitio.setImageURI(uri)
            selectedImageUri = uri  // Guardar URI de la imagen seleccionada
        } else {
            Log.i("aris", "No seleccionado")
        }
    }

    lateinit var btnImagen: Button
    lateinit var btnAgregarSitio: Button
    lateinit var ivSitio: ImageView
    lateinit var txtNombreDestino: EditText
    lateinit var txtDescripcionDestino: EditText
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_sitio)

        btnImagen = findViewById(R.id.btnImagen)
        btnAgregarSitio = findViewById(R.id.btnAgregarSitio)
        ivSitio = findViewById(R.id.ivSitio)
        txtNombreDestino = findViewById(R.id.txtNombreDestino)
        txtDescripcionDestino = findViewById(R.id.txtDescripcionDestino)

        btnImagen.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        btnAgregarSitio.setOnClickListener {
            agregarSitioTuristico()
        }
    }

    private fun agregarSitioTuristico() {
        val nombre = txtNombreDestino.text.toString()
        val descripcion = txtDescripcionDestino.text.toString()
        val imagen = selectedImageUri?.toString() ?: ""

        if (nombre.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty()) {
            val destino = DestinoTuristico(nombre = nombre, descripcion = descripcion, imagen = imagen)
            val dbHelper = DataBaseHelper(this)
            val id = dbHelper.insertDestinoTuristico(destino)

            if (id != -1L) {
                Toast.makeText(this, "Destino Turístico agregado exitosamente", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error al agregar Destino Turístico", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        txtNombreDestino.text.clear()
        txtDescripcionDestino.text.clear()
        ivSitio.setImageURI(null)
        selectedImageUri = null
    }
}
