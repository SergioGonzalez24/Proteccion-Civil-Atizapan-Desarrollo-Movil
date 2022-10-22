package mx.itesm.jmggm.atizapan

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import mx.itesm.jmggm.atizapan.model.UserDirectorio
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
//Clase para rellenar de manera correcta las tablas del directorio
/* A class that is used to create a list of items. */
class AdaptaTablas(private val context : Activity,private val arrayList: ArrayList<UserDirectorio>) : ArrayAdapter<UserDirectorio>(context,
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