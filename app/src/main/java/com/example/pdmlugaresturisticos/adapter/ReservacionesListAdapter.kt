package com.example.pdmlugaresturisticos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.example.pdmlugaresturisticos.R
import com.example.pdmlugaresturisticos.models.Reservacion
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.helper.DataBaseHelper

class ReservacionesListAdapter(
    private val context: Context,
    private val reservacionesList: List<Reservacion>,
    private val onCancelClickListener: OnCancelClickListener
) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int = reservacionesList.size

    override fun getItem(position: Int): Any = reservacionesList[position]

    override fun getItemId(position: Int): Long = reservacionesList[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: inflater.inflate(R.layout.list_item_reservacion, parent, false)

        val textViewReservacionId = view.findViewById<TextView>(R.id.textViewReservacionId)
        val textViewActividadNombre = view.findViewById<TextView>(R.id.textViewActividadNombre)
        val textViewActividadFecha = view.findViewById<TextView>(R.id.textViewActividadFecha)
        val textViewActividadCosto = view.findViewById<TextView>(R.id.textViewActividadCosto)
        val btnCancelarReservacion = view.findViewById<Button>(R.id.btnCancelarReservacion)

        val reservacion = reservacionesList[position]
        textViewReservacionId.text = "ID Reservación: ${reservacion.id}"

        val dbHelper = DataBaseHelper(context)
        val actividad = dbHelper.getActividadById(reservacion.idActividadTuristica)

        if (actividad != null) {
            textViewActividadNombre.text = "Nombre: ${actividad.nombre}"
            textViewActividadFecha.text = "Fecha: ${actividad.fecha}"
            textViewActividadCosto.text = "Costo: ${actividad.costo}"
        } else {
            textViewActividadNombre.text = "Nombre: N/A"
            textViewActividadFecha.text = "Fecha: N/A"
            textViewActividadCosto.text = "Costo: N/A"
        }

        btnCancelarReservacion.setOnClickListener {
            onCancelClickListener.onCancelClick(reservacion)
        }

        return view
    }

    interface OnCancelClickListener {
        fun onCancelClick(reservacion: Reservacion)
    }
}
