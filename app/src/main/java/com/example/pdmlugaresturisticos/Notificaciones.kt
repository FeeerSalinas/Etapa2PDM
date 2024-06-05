package com.example.pdmlugaresturisticos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmlugaresturisticos.adapter.ReservacionesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.Reservacion

class Notificaciones : AppCompatActivity() {

    private lateinit var listViewReservaciones: ListView
    private var reservacionesList: List<Reservacion> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notificaciones)

        listViewReservaciones = findViewById(R.id.listViewReservaciones)

        loadReservaciones()

        // Movilidad con los botones
        val btnPerfilUsuario: ImageButton = findViewById(R.id.btnPerfilUsuario)
        btnPerfilUsuario.setOnClickListener {
            val intent: Intent = Intent(this, Perfil::class.java)
            startActivity(intent)
        }

        val btnAnadirSitio: ImageButton = findViewById(R.id.btnHome)
        btnAnadirSitio.setOnClickListener {
            val intent: Intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)
        }

        val btnDestacados: ImageButton = findViewById(R.id.btnDestacados)
        btnDestacados.setOnClickListener {
            val intent: Intent = Intent(this, ViewActividadesTuristicas::class.java)
            startActivity(intent)
        }
    }

    private fun loadReservaciones() {
        val dbHelper = DataBaseHelper(this)
        reservacionesList = dbHelper.getAllReservaciones()

        val adapter = ReservacionesListAdapter(
            this,
            reservacionesList,
            object : ReservacionesListAdapter.OnCancelClickListener {
                override fun onCancelClick(reservacion: Reservacion) {
                    showCancelConfirmationDialog(reservacion)
                }
            }
        )
        listViewReservaciones.adapter = adapter
    }

    private fun showCancelConfirmationDialog(reservacion: Reservacion) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Cancelar Reservación")
            .setMessage("¿Estás seguro de que deseas cancelar esta reservación?")
            .setPositiveButton("Sí") { dialog, which ->
                cancelReservacion(reservacion)
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }

    private fun cancelReservacion(reservacion: Reservacion) {
        val dbHelper = DataBaseHelper(this)
        dbHelper.deleteReservacion(reservacion.id.toLong())  // Convertir Int a Long
        loadReservaciones()  // Actualiza la lista de reservaciones
    }
}