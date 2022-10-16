package mx.itesm.jmggm.atizapan.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.model.UserDirectorio
import mx.itesm.jmggm.atizapan.databinding.FragmentDirectoryBinding

class directoryFragment : Fragment() {
    private lateinit var binding: FragmentDirectoryBinding
    private lateinit var departamentoList: ArrayList<UserDirectorio>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentDirectoryBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDirectoryBinding.inflate(layoutInflater)
        //println("HOLA")
        val imageID = intArrayOf(
            R.drawable.logopc,R.drawable.shield, R.drawable.medicine,
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
            val departamento= UserDirectorio(nombre[i],telefono[i],imageID[i])
            departamentoList.add(departamento)
        }

    }
}