package mx.itesm.jmggm.atizapan.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.databinding.ActivityGoogleSignBinding
import mx.itesm.jmggm.atizapan.view.MainActivity.Companion.EXTRA_NAME

class GoogleSignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoogleSignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleSignBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textDisplayName.text = intent.getStringExtra(EXTRA_NAME)
        binding.logout.setOnClickListener {
            Firebase.auth.signOut()

            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}