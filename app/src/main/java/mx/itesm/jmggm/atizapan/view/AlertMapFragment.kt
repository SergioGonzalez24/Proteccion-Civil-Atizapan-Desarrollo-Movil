package mx.itesm.jmggm.atizapan.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.databinding.ActivityAlertMapFragmentBinding
import mx.itesm.jmggm.atizapan.viewmodel.AlertMapVM

class AlertMapFragment : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationClickListener{
    private lateinit var position: Location
    private var userid:Int=1
    private lateinit var binding:ActivityAlertMapFragmentBinding
    private val args:AlertMapFragmentArgs by navArgs()
    private lateinit var map:GoogleMap
    private val viewmodel:AlertMapVM by viewModels()
    companion object{
        const val RESQUEST_CODE_LOCATION=0
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMerker()
        map.setOnMyLocationButtonClickListener (this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
        map.setOnMyLocationChangeListener {
            this.position=it
            println("Cambio posicion")
        }
    }

    private fun createMerker() {
        val coordinate = LatLng(19.543548, -99.234876)
        val marker = MarkerOptions().position(coordinate).title("Atizapan")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordinate, 18f),
            4000,
            null
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAlertMapFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ocultarImg()
        createMapFragment()
        val prefs=getSharedPreferences("usuario", Context.MODE_PRIVATE)
        userid=prefs.getInt("user",0)
        registrarEventos()

    }
    
    @Suppress("UNREACHABLE_CODE", "UnusedEquals")
    private fun registrarEventos() {
            binding.btnSolicitarServicio.setOnClickListener {
                val user:Int=1000
                val tipoAlerta=args.tipoAlerta
                if(tipoAlerta=="Policia"){
                    viewmodel.enviarCoordenadas(userid,5,"${position.latitude},${position.longitude}")
                    Toast.makeText(this,
                        "Coordenadas ${position.latitude},${position.longitude} enviadas",
                        Toast.LENGTH_SHORT).show()

                } else if(tipoAlerta=="Bomberos"){
                    viewmodel.enviarCoordenadas(userid,2,"${position.latitude},${position.longitude}")
                    Toast.makeText(this,
                        "Coordenadas ${position.latitude},${position.longitude} enviadas",
                        Toast.LENGTH_SHORT).show()
                } else{
                    viewmodel.enviarCoordenadas(userid,3,"${position.latitude},${position.longitude}")
                    Toast.makeText(this,
                        "Coordenadas ${position.latitude},${position.longitude} enviadas",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }



    @Suppress("UNREACHABLE_CODE", "UnusedEquals")
    private fun ocultarImg() {
        val tipoAlerta=args.tipoAlerta
        if(tipoAlerta=="Policia"){
            binding.imgPolice.visibility=View.VISIBLE
            binding.imgFire.visibility= View.GONE
            binding.imgHealth.visibility=View.GONE
        } else if(tipoAlerta=="Bomberos"){
            binding.imgFire.visibility==View.VISIBLE
            binding.imgHealth.visibility=View.GONE
            binding.imgPolice.visibility=View.GONE
        } else{
            binding.imgHealth.visibility==View.VISIBLE
            binding.imgFire.visibility= View.GONE
            binding.imgPolice.visibility=View.GONE
        }
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.AlertMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun isLocationPermissionGranted() =
        ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    @SuppressLint("MissingPermission")
    private fun enableLocation() {
        if (!::map.isInitialized) return
        if (isLocationPermissionGranted()) {
            map.isMyLocationEnabled=true
        } else {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            Toast.makeText(
                this,
                "Aceptar los permisos de localizacion en ajustes.",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                RESQUEST_CODE_LOCATION
            )
        }
    }


    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled=false
            Toast.makeText(this, "Para activar la localizacion, en ajustas acepta los permisos.",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Boton pulsado",
            Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estas en ${p0.latitude},${p0.longitude}",
            Toast.LENGTH_SHORT).show()

    }

    override fun onMyLocationChange(location: Location) {

        this.position=location
    }

}