package mx.itesm.jmggm.atizapan.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import mx.itesm.jmggm.atizapan.BuildConfig
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.databinding.ActivityAlertMapFragmentBinding

class AlertMapFragment : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMyLocationButtonClickListener,GoogleMap.OnMyLocationChangeListener, GoogleMap.OnMyLocationClickListener{
    private lateinit var position: Location
    private lateinit var binding:ActivityAlertMapFragmentBinding
    private val args:AlertMapFragmentArgs by navArgs()
    private lateinit var map:GoogleMap
    // Cliente proveedor de ubicación
    private lateinit var clienteLocalizacion: FusedLocationProviderClient
    // Callback para manejar las actualizaciones de ubicación
    private lateinit var locationCallback: com.google.android.gms.location.LocationCallback
    val CODIGO_PERMISO_GPS=200
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
        //setContentView(R.layout.activity_alert_map_fragment)
        ocultarImg()
        createMapFragment()
        registrarEventos(position)

    }

    private fun registrarEventos(position: Location) {
            Toast.makeText(this, "Estas en ${position.latitude},${position.longitude}", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (! this::clienteLocalizacion.isInitialized) {
            clienteLocalizacion = LocationServices.getFusedLocationProviderClient(this)
        }
        //registrarEventos(position)
    }

    private fun configurarGPS() {
        if (tienePermiso()) {
            iniciarActualizacionesPosicion()
        } else {
            solicitarPermisos()
        }
    }

    private fun solicitarPermisos() {
        val requiereJustificacion = ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (requiereJustificacion) {
            mostrarDialogo()
        } else { // Pedir el permiso directo
            pedirPermisoUbicacion()
        }
    }


    private fun pedirPermisoUbicacion() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), CODIGO_PERMISO_GPS
        )
    }

    private fun mostrarDialogo() {
        val dialogo = AlertDialog.Builder(this)
            .setMessage("Necesitamos saber tu posición para generar alertas")
            .setPositiveButton("Aceptar") { dialog, which ->
                pedirPermisoUbicacion()
            }
            .setNeutralButton("Cancelar", null)
        dialogo.show()
    }

    private fun tienePermiso(): Boolean {
        val estado = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        return estado == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun iniciarActualizacionesPosicion() {

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

    /*
    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(com.google.android.gms.maps.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
     */

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
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RESQUEST_CODE_LOCATION -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                map.isMyLocationEnabled = true
            } else {
                Toast.makeText(this, "Para activar la localizacion, en ajustas acepta los permisos.", Toast.LENGTH_SHORT).show()
            }
            CODIGO_PERMISO_GPS->
                if (grantResults.isEmpty()) {
                } else if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                    iniciarActualizacionesPosicion()
                } else {
                    // Permiso negado
                    val dialogo = AlertDialog.Builder(this)
                    dialogo.setMessage("Esta app requiere GPS, ¿Quieres habilitarlo manualmente?")
                        .setPositiveButton("Aceptar", DialogInterface.OnClickListener { dialog, which ->
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts(
                                "package",
                                BuildConfig.APPLICATION_ID, null
                            )
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        })
                        .setNeutralButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
                            println("No hay forma de usar gps, cerrar la actividad")
                            //Deshabilitar funcionalidad
                        })
                        .setCancelable(false)
                    dialogo.show()
                }
            else->{}
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResumeFragments() {
        super.onResumeFragments()
        if(!::map.isInitialized) return
        if(!isLocationPermissionGranted()){
            map.isMyLocationEnabled=false
            Toast.makeText(this, "Para activar la localizacion, en ajustas acepta los permisos.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "Boton pulsado", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Estas en ${p0.latitude},${p0.longitude}", Toast.LENGTH_SHORT).show()

    }

    override fun onMyLocationChange(location: Location) {
        println("Hola")

        this.position=location
    }

}