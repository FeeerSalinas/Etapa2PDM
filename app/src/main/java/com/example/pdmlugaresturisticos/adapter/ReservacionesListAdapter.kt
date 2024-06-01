import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.pdmlugaresturisticos.R
import com.example.pdmlugaresturisticos.models.Reservacion

class ReservacionesListAdapter(
    private val context: Context,
    private val dataSource: List<Reservacion>
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
        val rowView = convertView ?: inflater.inflate(R.layout.list_item_reservacion, parent, false)

        val reservacion = getItem(position) as Reservacion

        val idReservaTextView = rowView.findViewById<TextView>(R.id.textViewIdReserva)
        val idActividadTextView = rowView.findViewById<TextView>(R.id.textViewIdActividad)

        "ID de Reserva: ${reservacion.id}".also { idReservaTextView.text = it }
        "ID de Actividad: ${reservacion.idActividadTuristica}".also { idActividadTextView.text = it }

        return rowView
    }
}
