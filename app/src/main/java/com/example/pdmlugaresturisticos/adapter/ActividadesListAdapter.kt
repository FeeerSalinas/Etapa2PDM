package com.example.pdmlugaresturisticos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pdmlugaresturisticos.R
import com.example.pdmlugaresturisticos.helper.DataBaseHelper
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.squareup.picasso.Picasso


class ActividadesListAdapter(private val context: Context, private val dataSource: List<ActividadTuristica>, private val onDeleteClickListener: OnDeleteClickListener) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return dataSource[position].id.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = convertView ?: inflater.inflate(R.layout.list_item_actividad_turistica, parent, false)

        val actividad = getItem(position) as ActividadTuristica

        val nombreTextView = rowView.findViewById<TextView>(R.id.textViewNombre)
        val descripcionTextView = rowView.findViewById<TextView>(R.id.textViewDescripcion)
        val fechaTextView = rowView.findViewById<TextView>(R.id.textViewFecha)
        val costoTextView = rowView.findViewById<TextView>(R.id.textViewCosto)
        val imagenImageView = rowView.findViewById<ImageView>(R.id.imageView)

        nombreTextView.text = actividad.nombre
        descripcionTextView.text = actividad.descripcion
        fechaTextView.text = actividad.fecha
        costoTextView.text = "Costo: \$${actividad.costo}"

        // Usar Picasso para cargar la imagen desde una URL
        Picasso.get().load(actividad.imagen).into(imagenImageView)

        val btnEliminarActividad = rowView.findViewById<ImageButton>(R.id.btnEliminarActividad)

        btnEliminarActividad.setOnClickListener {
            onDeleteClickListener.onDeleteClick(actividad)
        }
        // Dentro del método getView() de ActividadesListAdapter

        // Agregar el OnClickListener para reservar la actividad
        val btnReservarActividad = rowView.findViewById<ImageButton>(R.id.btnReservarActividad)
        btnReservarActividad.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Reservar Actividad")
                .setMessage("¿Estás seguro de que deseas reservar esta actividad?")
                .setPositiveButton("Reservar") { dialog, which ->
                    // Llamar al método para insertar la reserva en la base de datos
                    val dbHelper = DataBaseHelper(context)
                    val actividadId = actividad.id
                    // Establecer el ID de usuario predeterminado en 1
                    val userId = 1
                    dbHelper.insertReservacion(userId, actividadId.toInt())
                    // Actualizar la vista o realizar cualquier otra acción necesaria después de la reserva
                    // Por ejemplo, podrías mostrar un mensaje de confirmación
                    Toast.makeText(context, "Actividad reservada con éxito", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        return rowView
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(actividad: ActividadTuristica)
    }

}

