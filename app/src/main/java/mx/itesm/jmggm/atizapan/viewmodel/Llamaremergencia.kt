package mx.itesm.jmggm.atizapan.viewmodel

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding

class Llamaremergencia : AppCompatActivity() {
    val numeroEmergencia = "911"
    val REQUEST_PHONE_CALL = 1
    private lateinit var binding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        binding.btnEmergencia.setOnClickListener {

            if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_PHONE_CALL)
            }else{
                startCall()
                println("Esta llamando")
            }

        }

    }
    @SuppressLint("FaltaPermiso")
    private fun startCall() {
        val callIntent = Intent(Intent.ACTION_CALL)

        callIntent.data = Uri.parse("tel:$numeroEmergencia")

        startActivity(callIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PHONE_CALL)startCall()
    }
}