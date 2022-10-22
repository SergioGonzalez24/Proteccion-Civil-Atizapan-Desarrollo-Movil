package mx.itesm.jmggm.atizapan.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.jmggm.atizapan.databinding.ItemNotifBinding
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* This class is a subclass of RecyclerView.ViewHolder, and it's constructor takes a View as a
parameter, and it has a bind method that takes a String and a String as parameters. */
class NotifView(view: View):RecyclerView.ViewHolder(view) {
    private val binding=ItemNotifBinding.bind(view)
    fun bind(nombre:String, descripcion:String){
        binding.nombrelayout.setText(nombre)
        binding.descripcionlayout.setText(descripcion)
    }
}