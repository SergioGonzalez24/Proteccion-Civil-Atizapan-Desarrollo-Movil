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

/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/*Make a notification system that checks if the status of a report has changed, if it
has, it sends a notification to the user and chech if the user exists or not. */
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




   /**
    * A function that is called when the activity is created.
    * 
    * @param savedInstanceState A Bundle object containing the activity's previously saved state.
    */
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
        viewModel.regresar2.setOnClickListener{

            val x:Intent= Intent(this, BottomMenu::class.java)
            startActivity(x)
        }
        www()


    }



   /**
    * It creates a dialog box with a title, message, button, and a boolean value. If the boolean value
    * is true, it will open a new activity
    * 
    * @param titulo The title of the alert
    * @param mensaje The message you want to display.
    * @param button The text that will be displayed on the button.
    * @param isloged Boolean
    */
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

    /**
     * It creates a dialog with a title, message, and two buttons. The first button dismisses the
     * dialog, and the second button dismisses the dialog.
     * 
     * @param titulo The title of the dialog
     * @param mensaje The message you want to display.
     * @param button The text that will be displayed on the negative button.
     * @param button2 String = "Cancelar"
     */
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


 /**
  * The function creates a new user with the username and password from the text fields and then calls
  * the createNewUser function from the viewModel
  */
    private fun createUser() {
        val user  = User( viewModel.etUsername.text.toString() , viewModel.etPassword.text.toString())
        quoteViewModel.createNewUser(user)

    }

   /**
    * make a progress bar visible while the app is waiting for the response from the
    * server, and then make it invisible when the response is received.

    */
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


    /**
     * It saves the state of the variable isloged.
     * 
     * @param guardarEstado Bundle
     */
    override fun onSaveInstanceState(guardarEstado: Bundle) {
        super.onSaveInstanceState(guardarEstado)
        guardarEstado.putBoolean("variable", isloged!!)
    }

    override fun onRestoreInstanceState(recEstado: Bundle) {
        super.onRestoreInstanceState(recEstado)
        isloged = recEstado.getBoolean("variable")
    }


   /**
    * It creates a dialog with a circular progress bar.
    */
    fun cargarcirculo(){
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.load, null)
        builder.setView(view)
        dialogx=builder.create()
        dialogx.show()
    }

/**
 * If the device is running Android 8.0 or higher, create a notification channel with the specified ID,
 * name, and importance
 */
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


  /**
   * It creates a notification with a title, a context, a description and an id.
   * 
   * @param Titulo Title of the notification
   * @param Contex The text that appears in the notification bar.
   * @param Descripcion The text that will be displayed in the notification.
   * @param id_noti This is the notification ID. You can use this to update or cancel the notification
   * later.
   */
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


/**
 * It's a function that creates a thread that runs every 5 seconds and calls the getReporte() function.
 */
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





   /**
    * It checks if the status of a report has changed, if it has, it creates a notification.
    */
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