package com.example.pdmlugaresturisticos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmlugaresturisticos.adapter.ReservacionesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.Reservacion

class DetallesReservaciones : AppCompatActivity(), ReservacionesListAdapter.OnCancelClickListener {

    private lateinit var listViewReservaciones: ListView
    private lateinit var btnBack: ImageButton
    private var reservacionesList: List<Reservacion> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_reservaciones)

        listViewReservaciones = findViewById(R.id.listViewReservaciones)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, ViewActividadesTuristicas::class.java)
            startActivity(intent)
        }

        loadReservaciones()
    }

    private fun loadReservaciones() {
        val dbHelper = DataBaseHelper(this)
        val idUsuario = obtenerIdUsuarioSesion() // Método para obtener el ID del usuario que inició sesión

        // Obtener todas las reservaciones desde la base de datos
        val reservacionesList = dbHelper.getAllReservaciones()

        // Filtrar las reservaciones para que solo contengan las del usuario actual
        val reservacionesUsuario = reservacionesList.filter { it.idUsuario == idUsuario }

        // Crear y establecer el adaptador con la lista filtrada de reservaciones
        val adapter = ReservacionesListAdapter(this, reservacionesUsuario, this)
        listViewReservaciones.adapter = adapter
    }

    private fun obtenerIdUsuarioSesion(): Int {
        // Obtener el ID de usuario de SharedPreferences u otro mecanismo de sesión
        val sharedPref = getSharedPreferences("miapp", Context.MODE_PRIVATE)
        return sharedPref.getInt("idUsuario", -1) // -1 si no se encuentra el ID de usuario
    }


    override fun onCancelClick(reservacion: Reservacion) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Cancelar Reservación")
            .setMessage("¿Está seguro de que desea cancelar la reservación con ID ${reservacion.id}?")
            .setPositiveButton("Sí") { dialog, which ->
                val dbHelper = DataBaseHelper(this@DetallesReservaciones)
                val rowsAffected = dbHelper.deleteReservacion(reservacion.id.toLong())
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Reservación cancelada", Toast.LENGTH_SHORT).show()
                    loadReservaciones()
                } else {
                    Toast.makeText(this, "Error al cancelar la reservación", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }
}
