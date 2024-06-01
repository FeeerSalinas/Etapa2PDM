package com.example.pdmlugaresturisticos

import ReservacionesListAdapter
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmlugaresturisticos.adapter.ActividadesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.Reservacion

class DetallesReservaciones : AppCompatActivity(), ActividadesListAdapter.OnReserveClickListener {

    private lateinit var listViewReservaciones: ListView
    private lateinit var btnBack: ImageButton
    private var reservacionesList: List<Reservacion> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_reservaciones)

        listViewReservaciones = findViewById(R.id.listViewReservaciones)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            finish()
        }

        loadReservaciones()
    }

    private fun loadReservaciones() {
        val dbHelper = DataBaseHelper(this)
        reservacionesList = dbHelper.getAllReservaciones()

        val adapter = ReservacionesListAdapter(this, reservacionesList)
        listViewReservaciones.adapter = adapter
    }

    override fun onReserveClick(actividad: ActividadTuristica) {
        TODO("Not yet implemented")
    }
}
