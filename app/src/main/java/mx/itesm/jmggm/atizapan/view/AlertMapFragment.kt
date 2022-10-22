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
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* get the user's location and send it to the server */
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

/**
 * The function onMapReady is called when the map is ready to be used.
 * 
 * @param googleMap GoogleMap - The GoogleMap object that is ready to be used.
 */
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

/**
 * It creates a marker at a specific location and then animates the camera to that location.
 * 
 * The first line creates a LatLng object with the coordinates of the location. The second line creates
 * a MarkerOptions object with the position and title of the marker. The third line adds the marker to
 * the map. The fourth line animates the camera to the marker.
 * 
 * The animateCamera() function takes three parameters:
 * 
 *     The first parameter is a CameraUpdate object. In this case, we use the newLatLngZoom() function
 * to create a CameraUpdate object with the coordinates of the marker and the zoom level.
 *     The second parameter is the duration of the animation in milliseconds.
 *     The third parameter is an object that implements the GoogleMap.CancelableCallback interface.
 * This object is called when the animation is finished. We don't need to implement this interface, so
 * we pass null
 */
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
/**
 * Override fun onCreate(savedInstanceState: Bundle?) {
 *         super.onCreate(savedInstanceState)
 *         binding=ActivityAlertMapFragmentBinding.inflate(layoutInflater)
 *         setContentView(binding.root)
 *         ocultarImg()
 *         createMapFragment()
 *         val prefs=getSharedPreferences("usuario", Context.MODE_PRIVATE)
 *         userid=prefs.getInt("user",0)
 *         registrarEventos()
 * 
 *     }
 * 
 * @param savedInstanceState A Bundle object containing the activity's previously saved state. If the
 * activity has never existed before, the value of the Bundle object is null.
 */
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
    
/**
 * If the user selects the police option, the user's coordinates are sent to the police, if the user
 * selects the fire department option, the user's coordinates are sent to the fire department, and if
 * the user selects the ambulance option, the user's coordinates are sent to the ambulance.
 */
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



/**
 * "If the type of alert is police, show the police image and hide the other two, else if the type of
 * alert is fire, show the fire image and hide the other two, else show the health image and hide the
 * other two."
 * 
 * The function is called in the onCreateView method of the fragment:
 */
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

/**
 * Create a map fragment, and when the map is ready, call the onMapReady function.
 */
    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.AlertMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

/**
 * If the map is initialized and the location permission is granted, enable the location. Otherwise,
 * request the location permission
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

/**
 * If the user has already denied the permission, show a toast message. Otherwise, request the
 * permission
 */
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


/**
 * If the map is not initialized, return. If the location permission is not granted, disable the
 * location button and show a toast.
 * 
 * @return The current location of the device.
 */
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

/**
 * The function returns a boolean value. If the function returns true, the My Location button will not
 * operate and the caller is responsible for managing the user's location. If the function returns
 * false, the My Location button will operate as normal and the caller will not affect the My Location
 * button's behavior.
 * 
 * 
 * @return The return value is a boolean indicating whether the listener has consumed the event (i.e.,
 * the default behavior should not occur).
 */
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