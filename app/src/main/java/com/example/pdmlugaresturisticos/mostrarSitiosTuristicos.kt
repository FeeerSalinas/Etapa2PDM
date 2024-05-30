package com.example.pdmlugaresturisticos

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pdmlugaresturisticos.databinding.ActivityMainBinding
import com.example.pdmlugaresturisticos.databinding.ActivityMostrarSitiosTuristicosBinding
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.DestinoTuristico


class mostrarSitiosTuristicos : AppCompatActivity() {

    //para inyectar
    private lateinit var binding: ActivityMostrarSitiosTuristicosBinding

    //variables para marcar los datos
    private lateinit var dbHelper: DataBaseHelper
    private lateinit var adapterSitio: ArrayAdapter<String>

    //controladores

    private lateinit var listViewSitios: ListView
    private lateinit var txtNombre: EditText
    private lateinit var txtXDescripcion: EditText
    private lateinit var txtImagen: EditText
    private lateinit var editTextIdSitio: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMostrarSitiosTuristicosBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dbHelper = DataBaseHelper(this)

        //creando las variables de la vista
        txtNombre = findViewById(R.id.txtEditNombre)
        txtXDescripcion = findViewById(R.id.txtEditDescripcion)
        txtImagen = findViewById(R.id.txtEditImagen)
        listViewSitios = findViewById(R.id.listSitios)
        editTextIdSitio = findViewById(R.id.txtIdSitio)

        updateSitio2()

        //actualizando los registros
        val btnActualizarSitio: Button = findViewById(R.id.btnEditarSitio)
        btnActualizarSitio.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val descripcion = txtXDescripcion.text.toString()
            val imagen = txtImagen.text.toString()
            val id = editTextIdSitio.text.toString()

            Log.i("DATA", "IdSitio: "+id)

            if(!id.equals("") && id != null){
                dbHelper.updateSitio(id.toInt(), nombre.toString(), descripcion.toString(), imagen.toString())
                Toast.makeText(this, "El sitio se ha actualizado correctamente", Toast.LENGTH_SHORT).show()
                updateSitio2()
            }
            else{
                Toast.makeText(this, "No se ha seleccionado ningun sitio", Toast.LENGTH_SHORT).show()
            }

        }
        updateSitio2()

    }

    private fun updateSitio2(){
        val SitiosList: ArrayList<DestinoTuristico> = dbHelper.getAllSitios()
        binding.listSitios.adapter = AdapterSitio(this, SitiosList)
    }

    //metodo para actualizar los registros
    fun actualizarRegistro(view: View){
        val parentRow = view.parent as LinearLayout
        val parentRoot = parentRow.parent as LinearLayout
        val txtViewSitio = parentRoot.findViewById<View>(R.id.txtSitio) as TextView
        val txtSitio = txtViewSitio.text.toString()

        val txtViewIdSitio = parentRoot.findViewById<View>(R.id.hdSitioID) as TextView
        val txtIdSitio = txtViewIdSitio.text.toString()

        val partir_sitio = txtSitio.split("\n")
        val nombreSitio = partir_sitio[0]
        val detalleSitio = partir_sitio[1]
        val imagen = partir_sitio[2]

        txtNombre = findViewById(R.id.txtEditNombre)
        txtNombre.setText(nombreSitio)
        txtXDescripcion = findViewById(R.id.txtEditDescripcion)
        txtXDescripcion.setText(detalleSitio)
        txtImagen = findViewById(R.id.txtEditImagen)
        txtImagen.setText(imagen)

        editTextIdSitio = findViewById(R.id.txtIdSitio)
        editTextIdSitio.text = txtIdSitio
    }

    //metodo para eliminar registro
    fun deleteRegistro(view: View){
        val parentRow = view.parent as LinearLayout
        val parentRoot = parentRow.parent as LinearLayout

        val txtViewidSitio = parentRoot.findViewById<View>(R.id.hdSitioID) as TextView
        val txtIdSitio = txtViewidSitio.text.toString()

        val txtViewSitio = parentRoot.findViewById<View>(R.id.txtSitio) as TextView
        val txtSitio = txtViewSitio.text.toString()
        val partir_sitio = txtSitio.split("\n")
        val nombreSitio = partir_sitio[0]
        val descripcionSitio = partir_sitio[1]
        val imagen = partir_sitio[2]

        val builder = AlertDialog.Builder(this@mostrarSitiosTuristicos)
        builder.setMessage("Desea eliminar el registro $nombreSitio?")
            .setCancelable(false)
            .setPositiveButton("Si"){
                    dialog, id ->
                dbHelper.deleteSitios(txtIdSitio)
                updateSitio2()
                Toast.makeText(this, "Registro eliminado", Toast.LENGTH_SHORT).show()

            }
            .setNegativeButton("No"){
                    dialog, id ->
                dialog.dismiss()
            }

        val alert = builder.create()
        alert.show()

        Log.i("DATA", txtSitio)

    }

}