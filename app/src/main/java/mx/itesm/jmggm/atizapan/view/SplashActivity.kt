package mx.itesm.jmggm.atizapan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

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
        },3000)
    }
}