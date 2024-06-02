package com.example.pdmlugaresturisticos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.pdmlugaresturisticos.adapter.ReservacionesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper

class DetallesReservaciones : AppCompatActivity() {

    private lateinit var listViewReservaciones: ListView
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_reservaciones)

        listViewReservaciones = findViewById(R.id.listViewReservaciones)
        btnBack = findViewById(R.id.btnBack)

        btnBack.setOnClickListener {
            val intent = Intent(this, PaginaInicio::class.java)
            startActivity(intent)
        }

        loadReservaciones()
    }

    private fun loadReservaciones() {
        val dbHelper = DataBaseHelper(this)
        val reservacionesList = dbHelper.getAllReservaciones()

        val adapter = ReservacionesListAdapter(this, reservacionesList)
        listViewReservaciones.adapter = adapter
    }
}
