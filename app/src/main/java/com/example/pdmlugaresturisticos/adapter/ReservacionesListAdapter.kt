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

        val textViewReservacion = view.findViewById<TextView>(R.id.textViewReservacion)
        val btnCancelarReservacion = view.findViewById<Button>(R.id.btnCancelarReservacion)
        val reservacion = reservacionesList[position]

        textViewReservacion.text = "ID Reservación: ${reservacion.id}, ID Usuario: ${reservacion.idUsuario}, ID Actividad: ${reservacion.idActividadTuristica}"

        btnCancelarReservacion.setOnClickListener {
            onCancelClickListener.onCancelClick(reservacion)
        }

        return view
    }

    interface OnCancelClickListener {
        fun onCancelClick(reservacion: Reservacion)
    }
}
