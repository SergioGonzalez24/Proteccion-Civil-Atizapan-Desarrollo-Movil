package mx.itesm.jmggm.atizapan.view

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
//import mx.itesm.jmggm.atizapan.mainFragmentDirections
import mx.itesm.jmggm.atizapan.viewmodel.MainVM
import java.util.jar.Manifest


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

        binding.btnClima.setOnClickListener {
            val accion4 = mainFragmentDirections.actionMainFragmentToClimaActivity("Ver Clima")
            findNavController().navigate(accion4)
        }

        binding.btnEmergencia.setOnClickListener{
            val llamaintent = Intent(Intent.ACTION_DIAL)
            llamaintent.setData(Uri.parse("tel:911"))
            startActivity(llamaintent)
        }
        binding.btnToLog.setOnClickListener {
            val accion5=mainFragmentDirections.actionMainFragmentToMainActivity()
            findNavController().navigate(accion5)
        }
        binding.btnToSign.setOnClickListener {
            val accion5=mainFragmentDirections.actionMainFragmentToSignIn()
            findNavController().navigate(accion5)
        }
    }



}

