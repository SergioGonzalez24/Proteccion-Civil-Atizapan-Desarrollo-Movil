package mx.itesm.jmggm.atizapan

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AlertMapFragment : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener{


    private lateinit var map:GoogleMap
    companion object{
        const val RESQUEST_CODE_LOCATION=0
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMerker()
        map.setOnMyLocationButtonClickListener (this)
        map.setOnMyLocationClickListener(this)
        enableLocation()
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
        setContentView(R.layout.activity_alert_map_fragment)
        createMapFragment()
    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.AlertMap)
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
            else->{}
        }
    }

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
}