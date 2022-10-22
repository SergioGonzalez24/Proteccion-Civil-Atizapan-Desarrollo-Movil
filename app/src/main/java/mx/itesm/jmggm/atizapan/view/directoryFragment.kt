package mx.itesm.jmggm.atizapan.view

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import mx.itesm.jmggm.atizapan.databinding.FragmentDirectoryBinding

/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* A fragment that contains a list of emergency numbers and their respective locations. */
class directoryFragment : Fragment() /*,GoogleMap.OnMyLocationChangeListener*/ {
    private lateinit var binding: FragmentDirectoryBinding
    /*private lateinit var map:GoogleMap
    private lateinit var position: Location*/
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDirectoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RegistraEventos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDirectoryBinding.inflate(layoutInflater)

    }

    private fun RegistraEventos() {
        //Bomberos
        binding.llamaBomberos.setOnClickListener {
            val llamaintent1 = Intent(Intent.ACTION_DIAL)
            llamaintent1.setData(Uri.parse("tel:55 3622 1004"))
            startActivity(llamaintent1)
        }
        binding.locaBomberos.setOnClickListener {
            val locaintent1 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//numero+bomberos+atizapan/@19.5496381,-99.3110059,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21cf28904b983:0x5e70d6626953e63e!2m2!1d-99.2538177!2d19.5579213"))
            startActivity(locaintent1)
        }
        //PFC
        binding.llamaPFC.setOnClickListener {
            val llamaintent2 = Intent(Intent.ACTION_DIAL)
            llamaintent2.setData(Uri.parse("tel:55 5684 2142"))
            startActivity(llamaintent2)
        }
        binding.locaPFC.setOnClickListener {
            val locaintent2 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//Modulo+de+Policia+Estatal,+Calz.+S.+Mateo,+Atizapan+Centro,+52900+Cd+L%C3%B3pez+Mateos,+M%C3%A9x./@19.5629572,-99.3042293,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21cf092c49281:0x597e41d8f686b660!2m2!1d-99.2463631!2d19.5604723"))
            startActivity(locaintent2)
        }
        //PC
        binding.llamaPC.setOnClickListener {
            val llamaintent3 = Intent(Intent.ACTION_DIAL)
            llamaintent3.setData(Uri.parse("tel: 55 3622 1005"))
            startActivity(llamaintent3)
        }
        binding.locaPC.setOnClickListener {
            val locaintent3 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//numero+de+proteccion+civil+atizapan+de+zaragoza/@19.5496642,-99.3110059,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21d10625bc639:0x849c3720b2ce057a!2m2!1d-99.2538334!2d19.5579521"))
            startActivity(locaintent3)
        }
        //Emergencias
        binding.llamaEmergencias.setOnClickListener {
            val llamaintent4 = Intent(Intent.ACTION_DIAL)
            llamaintent4.setData(Uri.parse("tel: 55 53 66 71 93"))
            startActivity(llamaintent4)
        }
        binding.locaEmergencias.setOnClickListener {
            val locaintent4 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//c4+atizapan/@19.5493691,-99.3009411,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21cdf66a406e1:0x1a3648ba8c634f0c!2m2!1d-99.234578!2d19.5420144"))
            startActivity(locaintent4)
        }
        //CruzRoja
        binding.llamaCruzRoja.setOnClickListener {
            val llamaintent5 = Intent(Intent.ACTION_DIAL)
            llamaintent5.setData(Uri.parse("tel:52 55 58 22 21 88"))
            startActivity(llamaintent5)
        }
        binding.locaCruzRoja.setOnClickListener {
            val locaintent5 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//Cruz+Roja+Atizap%C3%A1n,+Los+Cajones,+Cd+L%C3%B3pez+Mateos,+M%C3%A9x./@19.527576,-99.2583386,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21cb5bf3597a9:0x3fb6134c34c26683!2m2!1d-99.2583386!2d19.527576"))
            startActivity(locaintent5)
        }
        //SeguridadPublica
        binding.llamaSeguridadPublica.setOnClickListener {
            val llamaintent6 = Intent(Intent.ACTION_DIAL)
            llamaintent6.setData(Uri.parse("tel: 55 36 22 27 30"))
            startActivity(llamaintent6)
        }
        //
        binding.locaSeguridadPublica.setOnClickListener {
            val locaintent6 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//Direcci%C3%B3n+de+Seguridad+P%C3%BAblica+y+Tr%C3%A1nsito+Municipal+de+Atizap%C3%A1n+de+Zaragoza.,+Zempoala+5,+Ignacio+Lopez+Rayon,+52986+Cd+L%C3%B3pez+Mateos,+M%C3%A9x./@19.5493691,-99.3009411,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21cdf6193cbd9:0x4e27884070c8f09c!2m2!1d-99.2345163!2d19.5418303"))
            startActivity(locaintent6)
        }
        //FugaS
        binding.llamaFugaS.setOnClickListener {
            val llamaintent7 = Intent(Intent.ACTION_DIAL)
            llamaintent7.setData(Uri.parse("tel:72 22 13 08 37"))
            startActivity(llamaintent7)
        }
        binding.locaFugaS.setOnClickListener {
            val locaintent7 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//FUGAS+DE+GAS+ATIZAPAN+NUMERO/@19.5752528,-99.2972505,13z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21c38ec231de5:0xb5216bd62fcc2aa4!2m2!1d-99.2489469!2d19.5943531"))
            startActivity(locaintent7)
        }
        //FugaA
        binding.llamaFugaA.setOnClickListener {
            val llamaintent8 = Intent(Intent.ACTION_DIAL)
            llamaintent8.setData(Uri.parse("tel:55 1083 6700"))
            startActivity(llamaintent8)
        }
        binding.locaFugA.setOnClickListener {
            val locaintent8 = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir//SAPASA,+Av.+Oc%C3%A9ano+Pac%C3%ADfico+80,+Lomas+Lindas,+52947+Cd+L%C3%B3pez+Mateos,+M%C3%A9x./@19.5716095,-99.2498919,17z/data=!4m8!4m7!1m0!1m5!1m1!1s0x85d21c5855e6fb37:0x8b3addb1fdd16801!2m2!1d-99.2477209!2d19.5715632"))
            startActivity(locaintent8)
        }

    }
}
