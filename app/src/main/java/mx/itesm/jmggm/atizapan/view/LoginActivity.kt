package mx.itesm.jmggm.atizapan.view

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.model.Login.User
import mx.itesm.jmggm.atizapan.model.Login.UserResponse
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel
import mx.itesm.jmggm.atizapan.databinding.ActivityLoginBinding
import mx.itesm.jmggm.atizapan.model.Login.RetroInstance
import mx.itesm.jmggm.atizapan.model.ReporteResponse
import mx.itesm.jmggm.atizapan.model.ResponseClass
import mx.itesm.jmggm.atizapan.model.RetroServiceInterface
import mx.itesm.jmggm.atizapan.viewmodel.AlertMapVM
import okhttp3.internal.ignoreIoExceptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
private lateinit var viewModel : ActivityLoginBinding

class MainActivity : AppCompatActivity() {
    lateinit var dialogx:AlertDialog
    private val viewmodel2: AlertMapVM by viewModels()
    private val quoteViewModel: MainActivityViewModel by viewModels()
    var isloged: Boolean = false
    var userid: Int=1
    companion object{
        const val MY_CHANNEL_ID = "myChannel"

        var id_noti=1
        var id_reporte= mutableListOf<ResponseClass>()

    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewModel.root
        setContentView(view)
        initViewModel()
        createChannel()

        viewModel.buttonSignin.setOnClickListener {
            if(viewModel.etUsername.text.toString().isEmpty()||viewModel.etPassword.text.toString().isEmpty()) {
                alerta("Aviso", "Ningún campo puede estar vacío","ok", isloged = false)
            }
            else{
                cargarcirculo()
                createUser()
            }
        }
        viewModel.buttonSignup.setOnClickListener{

            val x:Intent= Intent(this, SignIn::class.java)
            startActivity(x)
        }

        viewModel.buttonSinRegistro.setOnClickListener{
            alerta2("Aviso","Al entrar sin registrarte, por motivos de seguridad no podrás generar reportes","Prefiero registrarme","ok")
        }
        viewmodel2.objrespuesta.observe(this){
            id_reporte=it
        }
        www()


    }



    private fun alerta(titulo:String,mensaje:String,button:String, isloged: Boolean){
        val dialog = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setNegativeButton(button) { view, _ ->
                view.dismiss()

                if (isloged == true ){
                    val x:Intent= Intent(this, BottomMenu::class.java)
                    startActivity(x)
                }
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }

    private fun alerta2(titulo:String,mensaje:String,button:String,button2: String){
        val dialog = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setNegativeButton(button) { view, _ ->
                view.dismiss()

            }
            .setPositiveButton(button2) { view, _ ->
                view.dismiss()

            }
            .setCancelable(false)
            .create()

        dialog.show()

    }


    private fun createUser() {
        val user  = User( viewModel.etUsername.text.toString() , viewModel.etPassword.text.toString())
        quoteViewModel.createNewUser(user)

    }

    private fun initViewModel() {
        quoteViewModel.getCreateNewUserObserver().observe(this, Observer <UserResponse?>{
            if(it  == null) {
                Thread.sleep(3000)
                val circularProgressBar = findViewById<CircularProgressBar>(R.id.circularProgressBar3)
                circularProgressBar.visibility=View.INVISIBLE
                val textoProgressBar = findViewById<TextView>(R.id.cargando)
                textoProgressBar.visibility=View.INVISIBLE
                Toast.makeText(this@MainActivity, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
            } else {
                Thread.sleep(3000)
                val circularProgressBar = findViewById<CircularProgressBar>(R.id.circularProgressBar3)
                circularProgressBar.visibility=View.INVISIBLE
                val textoProgressBar = findViewById<TextView>(R.id.cargando)
                textoProgressBar.visibility=View.INVISIBLE
                if(it.estatus=="Credenciales exitosas"){
                    isloged=true
                    userid=it.user_id
                    val prefs2= getSharedPreferences("usuario", Context.MODE_PRIVATE)
                    val editor2= prefs2.edit()
                    editor2.putInt("user",userid)
                    editor2.commit()
                    val prefs= getSharedPreferences("logueo", Context.MODE_PRIVATE)
                    val editor= prefs.edit()
                    editor.putBoolean("log",isloged)
                    editor.commit()
                    alerta("Aviso",it.estatus.toString(),"ok", isloged!!)
                }
                alerta("Aviso",it.estatus.toString(),"ok", isloged!!)
            }
        })
    }


    override fun onSaveInstanceState(guardarEstado: Bundle) {
        super.onSaveInstanceState(guardarEstado)
        guardarEstado.putBoolean("variable", isloged!!)
    }

    override fun onRestoreInstanceState(recEstado: Bundle) {
        super.onRestoreInstanceState(recEstado)
        isloged = recEstado.getBoolean("variable")
    }


    fun cargarcirculo(){
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.load, null)
        builder.setView(view)
        dialogx=builder.create()
        dialogx.show()
    }

    fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MY_CHANNEL_ID,
                "MySuperChannel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "SUSCRIBETE"
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }


    fun createSimpleNotification(Titulo:String,Contex:String,Descripcion:String,id_noti:Int) {

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, flag)

        var builder = NotificationCompat.Builder(this, MY_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_delete)
            .setContentTitle(Titulo)
            .setContentText(Contex)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(Descripcion)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(id_noti, builder.build())
        }
    }


    private fun www() {
        val t = object : Thread() {
            override fun run() {
                while (!isInterrupted) {
                    try {
                        sleep(5000)
                        runOnUiThread {
                            //getAlerta()
                            println("esta sirviendo")
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

                            createSimpleNotification(
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