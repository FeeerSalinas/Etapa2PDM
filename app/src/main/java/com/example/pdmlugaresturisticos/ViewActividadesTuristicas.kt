package com.example.pdmlugaresturisticos

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pdmlugaresturisticos.adapter.ActividadesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import kotlinx.coroutines.launch

class ViewActividadesTuristicas : AppCompatActivity() {

    private lateinit var listViewActividades: ListView
    private lateinit var btnBack: ImageButton
    private var actividadesList: List<ActividadTuristica> = listOf()

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
        btnAgregar.setOnClickListener{

            val intent: Intent = Intent(this, ActividadesTuristicas::class.java)
            startActivity(intent)

        }
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            val intent: Intent = Intent(this, ViewActividadesTuristicas::class.java)
            startActivity(intent)
        }

        loadActividades()
    }

    private fun loadActividades() {
        lifecycleScope.launch {
            val dbHelper = DataBaseHelper(this@ViewActividadesTuristicas)
            actividadesList = dbHelper.getAllActividades()

            val adapter = ActividadesListAdapter(this@ViewActividadesTuristicas, actividadesList)
            listViewActividades.adapter = adapter
        }
    }
}
