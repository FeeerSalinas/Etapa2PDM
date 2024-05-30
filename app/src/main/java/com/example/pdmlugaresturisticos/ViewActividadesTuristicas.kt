package com.example.pdmlugaresturisticos

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.pdmlugaresturisticos.adapter.ActividadesListAdapter
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import kotlinx.coroutines.launch



class ViewActividadesTuristicas : AppCompatActivity(), ActividadesListAdapter.OnDeleteClickListener {

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


        loadActividades()
    }




    private fun loadActividades() {
        lifecycleScope.launch {
            val dbHelper = DataBaseHelper(this@ViewActividadesTuristicas)
            actividadesList = dbHelper.getAllActividades()

            val adapter = ActividadesListAdapter(this@ViewActividadesTuristicas, actividadesList, this@ViewActividadesTuristicas)
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
}
