package mx.itesm.jmggm.atizapan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.R
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* The SplashActivity class is a Kotlin class that extends the AppCompatActivity class. It has a
lateinit variable called handler that is of type Handler. It has an onCreate method that takes a
Bundle as a parameter. The onCreate method sets the content view to the splash_screen layout. It
then initializes the handler variable to a new Handler object. It then posts a runnable to the
handler that will start the BottomMenu activity after 5 seconds */
class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)


        handler = Handler()

        handler.postDelayed({
            val intent = Intent(this, BottomMenu::class.java)
            startActivity(intent)
            finish()
        },5000)
    }
}