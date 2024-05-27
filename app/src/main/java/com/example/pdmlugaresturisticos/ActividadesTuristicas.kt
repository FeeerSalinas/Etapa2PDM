package com.example.pdmlugaresturisticos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica

class ActividadesTuristicas : AppCompatActivity() {

    private lateinit var txtNombreActividad: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtImagen: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtCosto: EditText
    private lateinit var txtLugarTuristico: EditText
    private lateinit var btnAgregar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividades_turisticas)

        // Inicializar las vistas
        txtNombreActividad = findViewById(R.id.txtNombreActividad)
        txtDescripcion = findViewById(R.id.editText2)
        txtImagen = findViewById(R.id.txtImagen)
        txtFecha = findViewById(R.id.txtFecha)
        txtCosto = findViewById(R.id.txtCosto)
        txtLugarTuristico = findViewById(R.id.txtLugarTuristico)
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            agregarActividadTuristica()
    }
}

    private fun agregarActividadTuristica() {
        val nombreActividad = txtNombreActividad.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val imagen = txtImagen.text.toString()
        val fecha = txtFecha.text.toString()
        val costo = txtCosto.text.toString().toDoubleOrNull()
        val idDestinoTuristico = txtLugarTuristico.text.toString().toIntOrNull()

        if (nombreActividad.isNotEmpty() && descripcion.isNotEmpty() && imagen.isNotEmpty() &&
            fecha.isNotEmpty() && costo != null && idDestinoTuristico != null) {

            val actividad = ActividadTuristica(
                nombre = nombreActividad,
                descripcion = descripcion,
                imagen = imagen,
                fecha = fecha,
                costo = costo,
                idDestinoTuristico = idDestinoTuristico
            )

            val dbHelper = DataBaseHelper(this)
            val id = dbHelper.insertActividadTuristica(actividad)

            if (id != -1L) {
                Toast.makeText(this, "Actividad Turística agregada exitosamente", Toast.LENGTH_SHORT).show()
                clearFields()
            } else {
                Toast.makeText(this, "Error al agregar Actividad Turística", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearFields() {
        txtNombreActividad.text.clear()
        txtDescripcion.text.clear()
        txtImagen.text.clear()
        txtFecha.text.clear()
        txtCosto.text.clear()
        txtLugarTuristico.text.clear()
    }
}