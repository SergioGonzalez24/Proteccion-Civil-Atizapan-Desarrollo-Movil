package mx.itesm.jmggm.atizapan.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.jmggm.atizapan.databinding.ItemNotifBinding

class NotifView(view: View):RecyclerView.ViewHolder(view) {
    private val binding=ItemNotifBinding.bind(view)
    fun bind(nombre:String, descripcion:String){
        binding.nombrelayout.setText(nombre)
        binding.descripcionlayout.setText(descripcion)
    }
}