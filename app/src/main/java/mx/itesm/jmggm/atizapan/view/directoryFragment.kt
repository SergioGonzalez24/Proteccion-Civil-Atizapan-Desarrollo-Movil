package mx.itesm.jmggm.atizapan

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.jmggm.atizapan.databinding.FragmentDirectoryBinding

class directoryFragment : Fragment() {
    private lateinit var adaptador : AdaptaTablas
    private lateinit var recyclerView: RecyclerView
    private lateinit var directorioList: ArrayList<User>

    lateinit var imageid: Array<Int>
    lateinit var NoDepto: Array<String>
    lateinit var numero: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataInitialize()
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adaptador = AdaptaTablas(directorioList)
        recyclerView.adapter = adaptador
    }
    private fun dataInitialize(){
        directorioList = arrayListOf<User>()

        imageid = arrayOf(
            R.drawable.logopc,R.drawable.shield,R.drawable.medicine,
            R.drawable.policebadge,R.drawable.ssp,R.drawable.ar,
            R.drawable.pfc,R.drawable.fuga,R.drawable.fugas
        )
        NoDepto = arrayOf(
            "Protección Civil","Bomberos Atizapán","Cruz Roja",
            "Emergencias Atizapán","Seguridad Pública","Centro Antirrábico",
            "Policía Federal de Caminos","Fugas de Agua","Fugas de Gas"
        )
        numero = arrayOf(
            "5822 8151","55 3622 1004","55 5822 2547",
            "5822 2547","1106-2163","58 22 10 11",
            "5684 2142","01 800 201 24 89","58 24 97 26"
        )
        for(i in imageid.indices){
            val user = User(imageid[i],NoDepto[i],numero[i])
            directorioList.add(user)
        }

    }
}