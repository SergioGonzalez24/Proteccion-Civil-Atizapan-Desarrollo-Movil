package mx.itesm.jmggm.atizapan.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.model.Login.User
import mx.itesm.jmggm.atizapan.model.Login.UserResponse
import mx.itesm.jmggm.atizapan.viewmodel.MainActivityViewModel
import mx.itesm.jmggm.atizapan.databinding.ActivityLoginBinding


@SuppressLint("StaticFieldLeak")
private lateinit var viewModel : ActivityLoginBinding

class MainActivity : AppCompatActivity() {
    //private val _isloged=MutableLiveData<Boolean>(state["Logueado"]?:false)
    //val isLOGED: LiveData<Boolean> get()=_isloged
    private val quoteViewModel: MainActivityViewModel by viewModels()
    var isloged: Boolean = false
//    val prefs= getSharedPreferences("logueo", Context.MODE_PRIVATE)
//    val editor= prefs.edit()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ActivityLoginBinding.inflate(layoutInflater)
        val view = viewModel.root
        setContentView(view)
        initViewModel()

        viewModel.buttonSignin.setOnClickListener {
            if(viewModel.etUsername.text.toString().isEmpty()||viewModel.etPassword.text.toString().isEmpty()) {
            alerta("Aviso", "Ningún campo puede estar vacío","ok", isloged = false)
            }
            else{
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
//        val prefs= getSharedPreferences("logueo", Context.MODE_PRIVATE)
//        val editor= prefs.edit()
//        editor.putBoolean("log",isloged)
//        editor.commit()

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
                Toast.makeText(this@MainActivity, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
            } else {
                if(it.estatus=="Credenciales exitosas"){
                    isloged=true
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

}