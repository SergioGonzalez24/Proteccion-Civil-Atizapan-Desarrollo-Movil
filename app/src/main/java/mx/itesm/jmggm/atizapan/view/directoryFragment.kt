package mx.itesm.jmggm.atizapan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.jmggm.atizapan.databinding.FragmentDirectoryBinding

class directoryFragment : Fragment() {
    private lateinit var binding: FragmentDirectoryBinding
    private lateinit var departamentoList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDirectoryBinding.inflate(layoutInflater)

        val imageID = intArrayOf(
            R.drawable.logopc,R.drawable.shield,R.drawable.medicine,
            R.drawable.policebadge,R.drawable.ssp,R.drawable.ar,
            R.drawable.pfc,R.drawable.fuga,R.drawable.fugas
        )
        val nombre = arrayOf(
            "Protección Civil","Bomberos Atizapán","Cruz Roja",
            "Emergencias Atizapán","Seguridad Pública","Centro Antirrábico",
            "Policía Federal de Caminos","Fugas de Agua","Fugas de Gas"

        )
        val telefono = arrayOf(
            "5822 8151","55 3622 1004","55 5822 2547",
            "5822 2547","1106-2163","58 22 10 11",
            "5684 2142","01 800 201 24 89","58 24 97 26"
        )
        departamentoList = ArrayList()
        for(i in nombre.indices){
            val departamento= User(nombre[i],telefono[i],imageID[i])
            departamentoList.add(departamento)
        }

    }
}