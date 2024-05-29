package com.example.pdmlugaresturisticos

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.DestinoTuristico
import kotlinx.coroutines.launch

class ActividadesTuristicas : AppCompatActivity() {

    private lateinit var txtNombreActividad: EditText
    private lateinit var txtDescripcion: EditText
    private lateinit var txtImagen: EditText
    private lateinit var txtFecha: EditText
    private lateinit var txtCosto: EditText
    private lateinit var spinnerLugares: Spinner
    private lateinit var btnAgregar: Button

    private var lugaresList: List<DestinoTuristico> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividades_turisticas)

        // Inicializar las vistas
        txtNombreActividad = findViewById(R.id.txtNombreActividad)
        txtDescripcion = findViewById(R.id.editText2)
        txtImagen = findViewById(R.id.txtImagen)
        txtFecha = findViewById(R.id.txtFecha)
        txtCosto = findViewById(R.id.txtCosto)
        spinnerLugares = findViewById(R.id.spinnerLugares)
        btnAgregar = findViewById(R.id.btnAgregar)

        btnAgregar.setOnClickListener {
            agregarActividadTuristica()
        }

        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent: Intent = Intent(this, ViewActividadesTuristicas::class.java)
            startActivity(intent)
        }

        // Cargar los datos del Spinner desde la base de datos
        loadDestinos()
    }

    private fun loadDestinos() {
        lifecycleScope.launch {
            val dbHelper = DataBaseHelper(this@ActividadesTuristicas)
            lugaresList = dbHelper.getAllDestinos()

            val nombresLugares = lugaresList.map { it.nombre }
            val adapter = ArrayAdapter(this@ActividadesTuristicas, android.R.layout.simple_spinner_item, nombresLugares)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerLugares.adapter = adapter
        }
    }

    private fun agregarActividadTuristica() {
        val nombreActividad = txtNombreActividad.text.toString()
        val descripcion = txtDescripcion.text.toString()
        val imagen = txtImagen.text.toString()
        val fecha = txtFecha.text.toString()
        val costo = txtCosto.text.toString().toDoubleOrNull()
        val selectedPosition = spinnerLugares.selectedItemPosition
        val idDestinoTuristico = if (selectedPosition >= 0) lugaresList[selectedPosition].idDestinoTuristico else null

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
        spinnerLugares.setSelection(0)
    }
}
