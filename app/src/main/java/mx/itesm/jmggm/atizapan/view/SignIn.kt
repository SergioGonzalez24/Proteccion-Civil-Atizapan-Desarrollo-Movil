package mx.itesm.jmggm.atizapan.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.model.Login.RetroInstance
import mx.itesm.jmggm.atizapan.model.Login.RetroServiceInterface
import mx.itesm.jmggm.atizapan.model.Login.RetroServiceInterface2
import mx.itesm.jmggm.atizapan.model.Login.User
import mx.itesm.jmggm.atizapan.model.Login.UserAdd
import mx.itesm.jmggm.atizapan.model.Login.UserResponse
import mx.itesm.jmggm.atizapan.model.Login.UserResponseAdd
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel2
import mx.itesm.jmggm.atizapan.databinding.ActivitySignInBinding
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
/*create a user in the database, but before we have to check if the user exists, if it
does not exist, we create it, if it exists, we show an alert. */
@SuppressLint("StaticFieldLeak")
private lateinit var viewModel : ActivitySignInBinding


class SignIn : AppCompatActivity() {

    private val quoteViewModel: MainActivityViewModel2 by viewModels()
    lateinit var dialogx:AlertDialog
    var isloged:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ActivitySignInBinding.inflate(layoutInflater)
        val view = viewModel.root

        setContentView(view)
        initViewModel()
        viewModel.buttonConfirmarregistro.setOnClickListener {
            if(viewModel.etMail.text.toString().isEmpty()||viewModel.etPassword.text.toString().isEmpty()||viewModel.etFullName.text.toString().isEmpty()||viewModel.etUsername.text.toString().isEmpty()||viewModel.etPhone.text.toString().isEmpty()) {
                alerta("Aviso", "Ningún campo puede estar vacío","ok")
            }
            else{
                isloged=true
                val prefs= getSharedPreferences("logueo", Context.MODE_PRIVATE)
                val editor= prefs.edit()
                editor.putBoolean("log",isloged)
                editor.commit()
                cargarcirculo()
                createUserAdd()
            }
        }
        viewModel.regresar.setOnClickListener {
            val x:Intent= Intent(this,MainActivity::class.java)
            startActivity(x)
        }
    }


    private fun createUserAdd() {
        var x= User( viewModel.etMail.text.toString() ,viewModel.etFullName.text.toString())

        var xx:UserResponse?


        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
        val call = retroService.createUser(x)
        call.enqueue(object: Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                alerta("Aviso","Conexion fallida","ok" )
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful) {
                    xx=response.body()
                    if (xx?.estatus=="Contraseña Incorecta"||xx?.estatus=="Credenciales exitosas"){
                        alerta("Aviso","El usuario que deseas registrar ya existe","ok" )

                    }
                    else{

                        val user  = UserAdd( viewModel.etMail.text.toString() ,viewModel.etFullName.text.toString(),viewModel.etPassword.text.toString(),viewModel.etUsername.text.toString(),viewModel.etPhone.text.toString())
                        quoteViewModel.createNewUser(user)

                    }

                } else {
                    xx=null

                }
            }
        })

    }



/**
 * It creates an alert dialog with a title, message, and button.
 * 
 * The function takes three parameters:
 * 
 * * titulo: The title of the dialog
 * * mensaje: The message of the dialog
 * * button: The text of the button
 * 
 * The function creates an AlertDialog object and sets the title, message, and button.
 * 
 * The function then shows the dialog.
 * 
 * The function is called from the onCreate() method:
 * 
 *     alerta("Alerta","Esto es una alerta","Aceptar")
 * 
 * The function is called with the title, message, and button text.
 * 
 * The result is an alert dialog with the title, message, and button text.
 * 
 * ![alt text][logo]
 * 
 * [logo]: https://i.stack.imgur
 * 
 * @param titulo The title of the alert
 * @param mensaje The message you want to display.
 * @param button The text that will be displayed on the button.
 */
    private fun alerta(titulo:String,mensaje:String,button:String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setNegativeButton(button) { view, _ ->
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }


    private fun initViewModel() {
        quoteViewModel.getCreateNewUserObserver().observe(this, Observer <UserResponseAdd?>{

            if(it  == null) {
                Thread.sleep(3000)
                dialogx.dismiss()
                Toast.makeText(this@SignIn, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
            } else {
                Thread.sleep(3000)
                dialogx.dismiss()
                alerta2("Aviso",it.estatus.toString(),"ok" )

            }
        })
    }


    private fun alerta2(titulo:String,mensaje:String,button:String){
        val dialog = AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setNegativeButton(button) { view, _ ->
                view.dismiss()
                val x:Intent= Intent(this,MainActivity::class.java)
                startActivity(x)
            }
            .setCancelable(false)
            .create()

        dialog.show()

    }



/**
 * It creates a dialog with a circular progress bar
 */
    fun cargarcirculo(){
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.load, null)
        builder.setView(view)
        dialogx=builder.create()
        dialogx.show()
    }

}