package mx.itesm.jmggm.atizapan.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import mx.itesm.jmggm.atizapan.BottomMenu
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel
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
    private val checkLog:SignIn = SignIn()
    private val checkLog2:MainActivity = MainActivity()
    private val viewModel3 by viewModels<MainActivityViewModel>()
    //    val prefs=activity?.getSharedPreferences("logueo", Context.MODE_PRIVATE)
//    var isloged=prefs?.getBoolean("log",false)
    var ISLOGED:Boolean=false
    var ISLOGED2:Boolean=false


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

        binding=FragmentMainBinding.inflate((layoutInflater))
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val prefs=activity?.getSharedPreferences("logueo", Context.MODE_PRIVATE)
        var isloged=prefs?.getBoolean("log",false)
        val prefs2=activity?.getSharedPreferences("logueo", Context.MODE_PRIVATE)
        var isloged2=prefs2?.getBoolean("log",false)
        if(isloged!!||isloged2!!){
            ISLOGED=true
        }
        //Topicosuscribir
        Firebase.messaging.subscribeToTopic("alertasAtizapan")
            .addOnCompleteListener{ task ->
                var msg = "Suscrito"
                if (!task.isSuccessful){
                    msg = "Suscripcion fallida"
                    val suscribir= activity?.getSharedPreferences("suscrito", Context.MODE_PRIVATE)
                    val editor= suscribir?.edit()
                    if (editor != null) {
                        editor.putString("sus",msg)
                    }
                    if (editor != null) {
                        editor.commit()
                    }
                }
                println(msg)
            }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }




    private fun registrarEventos() {
        binding.btnPolice.setOnClickListener {
            if (ISLOGED){
                val accion = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Policia")
                findNavController().navigate(accion)
            }
            else {
                alerta2("IMPORTANTE!!","No es posible acceder a la funcionalidades de solicitar servicio sin antes registrarse.","Prefiero registrarme","ok")
            }
        }
        binding.btnFire.setOnClickListener {
            if(ISLOGED){
                val accion2 = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Bomberos")
                findNavController().navigate(accion2)
            }
            else{
                alerta2("IMPORTANTE!!","No es posible acceder a la funcionalidades de solicitar servicio sin antes registrarse.","Prefiero registrarme","ok")
            }
        }
        binding.btnHealth.setOnClickListener {
            if(ISLOGED){
                val accion3 = mainFragmentDirections.actionMainFragmentToAlertMapFragment("Ambulancia")
                findNavController().navigate(accion3)
            }
            else{
                alerta2("IMPORTANTE!!","No es posible acceder a la funcionalidades de solicitar servicio sin antes registrarse.","Prefiero registrarme","ok")
            }
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

        binding.btnServicios.setOnClickListener {
            val mapaServicios = Intent(android.content.Intent.ACTION_VIEW)
            mapaServicios.data= Uri.parse("https://www.google.com/maps/d/u/0/edit?mid=1qVCt320vGifhwgzOM5rHIZQAmbxVt-g&usp=sharing")
            startActivity(mapaServicios)
        }
    }

    private fun alerta2(titulo:String,mensaje:String,button:String,button2: String){
        val dialog = AlertDialog.Builder(this.requireContext())
            .setTitle(titulo)
            .setMessage(mensaje)
            .setNegativeButton(button) { view, _ ->
                view.dismiss()
                val accion=mainFragmentDirections.actionMainFragmentToSignIn()
                findNavController().navigate(accion)

            }
            .setPositiveButton(button2) { view, _ ->
                view.dismiss()

            }
            .setCancelable(false)
            .create()

        dialog.show()


    }

}
