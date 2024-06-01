package com.example.pdmlugaresturisticos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.pdmlugaresturisticos.R
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.squareup.picasso.Picasso

class ActividadesListAdapter(
    private val context: Context,
    private val dataSource: List<ActividadTuristica>,
    private val onDeleteClickListener: OnDeleteClickListener,
    private val onReserveClickListener: OnReserveClickListener
) : BaseAdapter() {

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

      /*  val btnReservarActividad = rowView.findViewById<ImageButton>(R.id.btnReservarActividad)
        btnReservarActividad.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Reservar Actividad Turística")
                .setMessage("¿Deseas completar la reserva de esta actividad turística?")
                .setPositiveButton("Sí") { dialog, which ->
                    onReserveClickListener.onReserveClick(actividad)
                }
                .setNegativeButton("No", null)
                .create()

            alertDialog.show()
        }*/
        val btnReservarActividad = rowView.findViewById<ImageButton>(R.id.btnReservarActividad)
        btnReservarActividad.setOnClickListener {
            onReserveClickListener.onReserveClick(actividad) // Llamar al método de reserva
        }

        return rowView
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(actividad: ActividadTuristica)
    }

    interface OnReserveClickListener {
        fun onReserveClick(actividad: ActividadTuristica)
    }
}
