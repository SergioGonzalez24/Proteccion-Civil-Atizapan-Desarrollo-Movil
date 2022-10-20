package mx.itesm.jmggm.atizapan.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.view.NotifView

class NotifAdapter(val nombre: MutableList<String>, val descripcion:MutableList<String>):RecyclerView.Adapter<NotifView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifView {
        val layoutInflater:LayoutInflater=LayoutInflater.from(parent.context)
        return NotifView(layoutInflater.inflate(R.layout.item_notif,parent,false))
    }

    override fun onBindViewHolder(holder: NotifView, position: Int) {
        val item=nombre[position]
        val item2=descripcion[position]
        holder.bind(item, item2)
    }

    override fun getItemCount(): Int=nombre.size


}