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
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
import mx.itesm.jmggm.atizapan.model.ReporteResponse
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel
//import mx.itesm.jmggm.atizapan.mainFragmentDirections
import mx.itesm.jmggm.atizapan.viewmodel.MainVM
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import mx.itesm.jmggm.atizapan.model.RetroInstance
import mx.itesm.jmggm.atizapan.model.RetroServiceInterface
import okhttp3.internal.ignoreIoExceptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    companion object{
        const val MY_CHANNEL_ID = "myChannel"
        var id_alerta =1
        var id_noti=1
        var id_reporte= mutableListOf<ReporteResponse>()
    }


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

    private fun www() {
        val t = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(5000)
                        checkLog2.runOnUiThread {
                            //getAlerta()
                            println("esta sirviendo")
                            //getReporte()

                        }

                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
        }
        t.start()


}

    fun getReporte() {


        for (i in id_reporte) {


            var xx2: ReporteResponse?


            val retroService =
                RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
            val call = retroService.checkAlerta("api/reporte/${i.id}")
            call.enqueue(object : Callback<ReporteResponse> {
                override fun onFailure(call: Call<ReporteResponse>, t: Throwable) {
                    ignoreIoExceptions { }
                }

                override fun onResponse(
                    call: Call<ReporteResponse>,
                    response: Response<ReporteResponse>
                ) {
                    if (response.isSuccessful) {

                        xx2 = response.body()
                        if(xx2?.estatus!=i.estatus){

                            checkLog2.createSimpleNotification(
                                "Notificacion Atizapan",
                                "Tu reporte id:${i.id} se ha actualizado",
                                "El estatus de tu reporte con ID:${i.id} ha cambiado de ${i.estatus?.uppercase()} a ${xx2?.estatus?.uppercase()}",
                                id_noti
                            )
                            id_noti++
                            if(xx2?.estatus=="Finalizado"){id_reporte.remove(i)}
                            else{

                                id_reporte[id_reporte.indexOf(i)].estatus=xx2?.estatus



                            }

                        }


                    }

                }
            })

        }
    }

}
