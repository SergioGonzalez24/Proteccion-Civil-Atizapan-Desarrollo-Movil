package mx.itesm.jmggm.atizapan

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import mx.itesm.jmggm.atizapan.databinding.ActivityBottomMenuBinding
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* This class is the main activity of the app, it's the activity that is launched when the app is
opened. It contains a bottom navigation bar that allows the user to navigate between the different
fragments of the app */
class BottomMenu : AppCompatActivity() {

    private lateinit var binding: ActivityBottomMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBottomMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().token.addOnCompleteListener{ task: Task<String> ->
            if(!task.isSuccessful){
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d("PUSH_TOKEN", "pushToken: $token")
            val prefs= getSharedPreferences("tokenotif", Context.MODE_PRIVATE)
            val editor= prefs.edit()
            editor.putString("tok",token)
            editor.commit()
        }

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_bottom_menu)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment,
                R.id.newsFragment,
                R.id.directoryFragment2,
                R.id.signOutFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


}