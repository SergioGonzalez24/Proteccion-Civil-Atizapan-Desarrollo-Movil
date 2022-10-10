package mx.itesm.jmggm.atizapan.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import mx.itesm.jmggm.atizapan.AlertMapFragment
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
//import mx.itesm.jmggm.atizapan.mainFragmentDirections
import mx.itesm.jmggm.atizapan.viewmodel.mainVM




/**
 * A simple [Fragment] subclass.
 * Use the [mainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mainFragment : Fragment() {

    private val viewModel:mainVM by viewModels()
    private lateinit var binding: FragmentMainBinding

    // Código para solicitar permiso de usar la ubicación
    private val CODIGO_PERMISO_GPS = 200

    // Cliente proveedor de ubicación
    //private lateinit var clienteLocalizacion: FusedLocationProviderClient

    // Callback para manejar las actualizaciones de ubicación
    //private lateinit var locationCallback: LocationCallback

    // Para saber si las actualizaciones están activas entre corridas de la app
    private var actualizandoPosicion: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentMainBinding.inflate((layoutInflater))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }

    private fun registrarEventos() {
        binding.btnPolice.setOnClickListener {
            val actMapa= Intent(this, AlertMapFragment::class.java)
            startActivity(actMapa)
        }
        binding.btnFire.setOnClickListener {
            val actMapa= Intent(this, AlertMapFragment::class.java)
            startActivity(actMapa)
        }
        binding.btnHealth.setOnClickListener {
            val actMapa= Intent(this, AlertMapFragment::class.java)
            startActivity(actMapa)
        }
    }

}