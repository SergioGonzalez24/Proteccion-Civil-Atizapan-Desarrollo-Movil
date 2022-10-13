package mx.itesm.jmggm.atizapan

import android.app.Activity
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView


//Clase para rellenar de manera correcta las tablas del directorio
class AdaptaTablas(private val lista: ArrayList<User>) : RecyclerView.Adapter<AdaptaTablas.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptaTablas.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdaptaTablas.MyViewHolder, position: Int) {
        val currentItem = lista[position]
        holder.Imagen.setImageResource(currentItem.imageID)
        holder.NoDepartamento.text = currentItem.nombre
        holder.NoTelefono.text= currentItem.telefono
    }

    override fun getItemCount(): Int {
        return lista.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val Imagen : ShapeableImageView = itemView.findViewById(R.id.profile_pic)
        val NoDepartamento : TextView = itemView.findViewById(R.id.departamento)
        val NoTelefono : TextView = itemView.findViewById(R.id.Numero)
    }

}