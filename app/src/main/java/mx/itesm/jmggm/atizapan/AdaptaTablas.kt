package mx.itesm.jmggm.atizapan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import mx.itesm.jmggm.atizapan.model.User

//Clase para rellenar de manera correcta las tablas del directorio
class AdaptaTablas(private val context : Activity,private val arrayList: ArrayList<User>) : ArrayAdapter<User>(context,
R.layout.list_item,arrayList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_item,null)
        val imageView : ImageView = view.findViewById(R.id.profile_pic)
        val departamento: TextView = view.findViewById(R.id.departamento)
        val numero: TextView = view.findViewById(R.id.Numero)
        imageView.setImageResource(arrayList[position].imageID)
        departamento.text = arrayList[position].nombre
        numero.text = arrayList[position].telefono
//Agregar elementos de una lista en las posiciónes correctas por id de xml


        return view

    }
}