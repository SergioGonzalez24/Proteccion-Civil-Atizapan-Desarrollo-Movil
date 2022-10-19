package mx.itesm.jmggm.atizapan

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
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}