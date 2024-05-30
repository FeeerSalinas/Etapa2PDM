package com.example.pdmlugaresturisticos

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.pdmlugaresturisticos.models.ActividadTuristica
import com.example.pdmlugaresturisticos.models.DestinoTuristico

class AdapterSitio(private val context: Activity,
    private val arrayList: ArrayList<DestinoTuristico>
    ) : ArrayAdapter<DestinoTuristico>(context, R.layout.list_item, arrayList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item, null)

        val txtLabel : TextView = view.findViewById(R.id.txtSitio)
        txtLabel.text = arrayList[position].nombre + "\n" + arrayList[position].descripcion + "\n" + arrayList[position].imagen

        val txtIdSitio : TextView = view.findViewById(R.id.hdSitioID)
        txtIdSitio.text = arrayList[position].idDestinoTuristico.toString()

        return view
    }

}