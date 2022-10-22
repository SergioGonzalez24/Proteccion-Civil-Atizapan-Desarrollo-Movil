package mx.itesm.jmggm.atizapan.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.fragment.findNavController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel
import mx.itesm.jmggm.atizapan.viewmodel.MainVM
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.model.*
import mx.itesm.jmggm.atizapan.model.Login.RetroServiceInterfaceAlerta
import mx.itesm.jmggm.atizapan.view.MainActivity.Companion.id_noti
import mx.itesm.jmggm.atizapan.view.MainActivity.Companion.id_reporte
import okhttp3.internal.ignoreIoExceptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Collections.list


/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* get the data from the API and show it in the textViews is the main screen*/
class mainFragment : Fragment() {

   /* A declaration of variables. */
    private val viewModel:MainVM by viewModels()
    private lateinit var binding: FragmentMainBinding
    private val checkLog:SignIn = SignIn()
    private val checkLog2:MainActivity = MainActivity()
    private val viewModel3 by viewModels<MainActivityViewModel>()
    private lateinit var adapter: NotifAdapter
    private var nombreList= mutableListOf<String>()
    private var descriptList= mutableListOf<String>()


/* A declaration of variables. */
    var ISLOGED:Boolean=false
    companion object{
        const val MY_CHANNEL_ID = "myChannel"
        var id_alerta =1
        var id_noti=1
        var id_reporte= mutableListOf<ReporteResponse>()

        lateinit var conjuntoAlertas: List<alerta>
    }


/* A code for the GPS. */
    private val CODIGO_PERMISO_GPS = 200

    private var actualizandoPosicion: Boolean = false


/**
 * Override fun onCreateView(
 *         inflater: LayoutInflater, container: ViewGroup?,
 *         savedInstanceState: Bundle?
 * 
 *     ): View? {
 * 
 *         binding=FragmentMainBinding.inflate((layoutInflater))
 *         return binding.root
 * 
 * 
 * 
 *     }
 * 
 * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment,
 * @param container The ViewGroup into which the fragment should be placed.
 * @param savedInstanceState Bundle?
 * @return The root of the binding object.
 */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        binding=FragmentMainBinding.inflate((layoutInflater))
        return binding.root



    }

/**
 * I'm trying to get the value of a variable from a sharedpreferences file, but it's not working.
 */
    override fun onStart() {
        super.onStart()
        println("prueba")
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
                        println(msg)
                    }
                    if (editor != null) {
                        editor.commit()
                        println(msg)
                    }
                }
                println(msg)
            }
        val prefs=activity?.getSharedPreferences("logueo", Context.MODE_PRIVATE)
        var isloged=prefs?.getBoolean("log",false)
        if(isloged!!){
            ISLOGED=true
        }
        else {
            ISLOGED = false
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
        www()


    }




    /**
     * It's a function that registers the events of the buttons in the main screen of the application
     */
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

   /**
    * It creates a dialog with two buttons, one of them closes the dialog and the other one closes the
    * dialog and navigates to another fragment.
    * 
    * @param titulo Title of the alert
    * @param mensaje String
    * @param button String
    * @param button2 String
    */
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


    /**
     * It's a function that runs a thread that runs a function that runs a thread that runs a function
     * that runs a thread that runs a function that runs a thread that runs a function that runs a
     * thread that runs a function that runs a thread that runs a function that runs a thread that runs
     * a function that runs a thread that runs a function that runs a thread that runs a function that
     * runs a thread that runs a function that runs a thread that runs a function that runs a thread
     * that runs a function that runs a thread that runs a function that runs a thread that runs a
     * function that runs a thread that runs a function that runs a thread that runs a function that
     * runs a thread that runs a function that runs a thread that runs a function that runs a thread
     * that runs a function that runs a thread that runs a function that runs a thread that runs a
     * function that runs a thread that runs a function that runs a thread that runs a function that
     * runs a thread that runs a
     */
    private fun www() {
        val t = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(5000)
                        checkLog2.runOnUiThread {
                            getReporte()

                        }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        t.start()


    }


    /**
     *  get the last 4 elements of an array and put them in a textview.
     */
    fun getReporte() {

        val retroService =
            RetroInstance.getRetroInstance().create(RetroServiceInterfaceAlerta::class.java)
        val call = retroService.Alertas("api/alerta/showall")
        call.enqueue(object : Callback<alertaResponse> {
            override fun onFailure(call: Call<alertaResponse>, t: Throwable) {
                ignoreIoExceptions { }
            }

            override fun onResponse(
                call: Call<alertaResponse>,
                response: Response<alertaResponse>
            ) {
                if (response.isSuccessful) {
                    conjuntoAlertas=response.body()!!.alertas
                    binding.textVieww1.setText(" Tipo:  "+ conjuntoAlertas[conjuntoAlertas.count()-1].nombre+ System.getProperty ("line.separator") + " Fecha:  "+ conjuntoAlertas[conjuntoAlertas.count()-1].order_date+ System.getProperty ("line.separator")+" Descripción:  "+ conjuntoAlertas[conjuntoAlertas.count()-1].descripcion)
                    binding.textVieww2.setText(" Tipo:  "+ conjuntoAlertas[conjuntoAlertas.count()-2].nombre+ System.getProperty ("line.separator") + " Fecha:  "+ conjuntoAlertas[conjuntoAlertas.count()-2].order_date+ System.getProperty ("line.separator")+" Descripción:  "+ conjuntoAlertas[conjuntoAlertas.count()-2].descripcion)
                    binding.textVieww3.setText(" Tipo:  "+ conjuntoAlertas[conjuntoAlertas.count()-3].nombre+ System.getProperty ("line.separator") + " Fecha:  "+ conjuntoAlertas[conjuntoAlertas.count()-3].order_date+ System.getProperty ("line.separator")+" Descripción:  "+ conjuntoAlertas[conjuntoAlertas.count()-3].descripcion)
                    binding.textVieww4.setText(" Tipo:  "+ conjuntoAlertas[conjuntoAlertas.count()-4].nombre+ System.getProperty ("line.separator") + " Fecha:  "+ conjuntoAlertas[conjuntoAlertas.count()-4].order_date+ System.getProperty ("line.separator")+" Descripción:  "+ conjuntoAlertas[conjuntoAlertas.count()-4].descripcion)

                }

            }
        })

    }


}