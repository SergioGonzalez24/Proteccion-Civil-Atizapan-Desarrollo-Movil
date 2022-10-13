package mx.itesm.jmggm.atizapan.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
//import mx.itesm.jmggm.atizapan.mainFragmentDirections
import mx.itesm.jmggm.atizapan.viewmodel.MainVM




/**
 * A simple [Fragment] subclass.
 * Use the [mainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class mainFragment : Fragment() {

    private val viewModel:MainVM by viewModels()
    private lateinit var binding: FragmentMainBinding
    //private var temp:Double?=null

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
        registrarWeather()
    }

    private fun registrarWeather() {
        val lat ="19.543548"
        val lon="-99.234876"
        val exclude ="current"
        val apiKey="68b800823cf8c5d32ecea64627e8c994"
        viewModel.conseguirDatosClima(lat,lon,exclude,apiKey)
        viewModel.temp.observe(viewLifecycleOwner){temperatura->
            binding.outTemp.setText(temperatura.toString())
        }
    }

    private fun registrarEventos() {
        binding.btnPolice.setOnClickListener {
            val accion = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Policia")
            findNavController().navigate(accion)
        }
        binding.btnFire.setOnClickListener {
            val accion2 = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Bomberos")
            findNavController().navigate(accion2)
        }
        binding.btnHealth.setOnClickListener {
            val accion3 = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Ambulancia")
            findNavController().navigate(accion3)
        }
    }

}