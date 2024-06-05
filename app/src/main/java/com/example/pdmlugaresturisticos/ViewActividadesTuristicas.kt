package com.example.pdmlugaresturisticos

import ActividadesListAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.Usuario
import kotlinx.coroutines.launch



class ViewActividadesTuristicas : AppCompatActivity(),
    ActividadesListAdapter.OnDeleteClickListener,
    ActividadesListAdapter.OnReserveClickListener {

    private lateinit var listViewActividades: ListView
    private lateinit var btnBack: ImageButton
    private var actividadesList: List<ActividadTuristica> = listOf()
    private val sharedPref = getSharedPreferences("miapp", Context.MODE_PRIVATE)
    private val idRol = sharedPref.getInt("idRol",-1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_actividades_turisticas)

        listViewActividades = findViewById(R.id.listViewActividades)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)
        }
        val btnAgregar: ImageButton = findViewById(R.id.btnAgregar)
        btnAgregar.setOnClickListener {
            val intent: Intent = Intent(this, ActividadesTuristicas::class.java)
            startActivity(intent)

        }
        // Verificar si el rol es admin (idRol == 1)
        if (idRol == 1) {
            // Mostrar el botón si el usuario es admin

            btnAgregar.visibility = View.VISIBLE
        } else {
            // Ocultar el botón si el usuario no es admin
            btnAgregar.visibility = View.GONE
        }


        val btnReservaciones: ImageButton = findViewById(R.id.btnReservaciones)
        btnReservaciones.setOnClickListener {
            val intent = Intent(this, DetallesReservaciones::class.java)
            startActivity(intent)
        }

        loadActividades()
    }


    private fun loadActividades() {
        lifecycleScope.launch {
            val dbHelper = DataBaseHelper(this@ViewActividadesTuristicas)
            actividadesList = dbHelper.getAllActividades()

            val adapter = ActividadesListAdapter(
                this@ViewActividadesTuristicas,
                actividadesList,
                this@ViewActividadesTuristicas,
                this@ViewActividadesTuristicas,
                idRol
            )
            listViewActividades.adapter = adapter
        }
    }

    override fun onDeleteClick(actividad: ActividadTuristica) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Eliminar Actividad Turística")
            .setMessage("¿Estás seguro de que deseas eliminar esta actividad turística?")
            .setPositiveButton("Eliminar") { dialog, which ->
                val dbHelper = DataBaseHelper(this@ViewActividadesTuristicas)
                dbHelper.deleteActividadTuristica(actividad.id.toLong())
                loadActividades()
            }
            .setNegativeButton("Cancelar", null)
            .create()

        alertDialog.show()
    }

    override fun onReserveClick(actividad: ActividadTuristica) {
        // Crear y mostrar un diálogo de confirmación
        AlertDialog.Builder(this)
            .setTitle("Confirmar Reservación")
            .setMessage("¿Está seguro de que desea realizar la reservación para ${actividad.nombre}?")
            .setPositiveButton("Sí") { dialog, which ->
                realizarReservacion(actividad)
            }
            .setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun realizarReservacion(actividad: ActividadTuristica) {
        val dbHelper = DataBaseHelper(this@ViewActividadesTuristicas)
        val sharedPref = getSharedPreferences("miapp", Context.MODE_PRIVATE)
        val idUsuario = sharedPref.getInt("idUsuario", -1)

        if (idUsuario == -1) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_LONG).show()
            return
        }

        val idReservacion: Long
        try {
            idReservacion = dbHelper.insertReserva(idUsuario, actividad.id.toLong())
            Log.d("Reservacion", "ID Usuario: $idUsuario, ID Actividad: ${actividad.id}, ID Reservacion: $idReservacion")
        } catch (e: Exception) {
            Log.e("Error", "Error al realizar la reservación: ${e.message}", e)
            Toast.makeText(this, "Error al realizar la reservación: ${e.message}", Toast.LENGTH_LONG).show()
            return
        }

        if (idReservacion != -1L) {
            Toast.makeText(this, "Reservación exitosa", Toast.LENGTH_SHORT).show()
            loadActividades()
        } else {
            Toast.makeText(this, "Error al realizar la reservación", Toast.LENGTH_SHORT).show()
        }
    }
}
