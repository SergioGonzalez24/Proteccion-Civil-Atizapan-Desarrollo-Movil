package mx.itesm.jmggm.atizapan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import mx.itesm.jmggm.atizapan.view.MainFrag

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        handler = Handler()

        handler.postDelayed({
            val intent = Intent(this, MainFrag::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}