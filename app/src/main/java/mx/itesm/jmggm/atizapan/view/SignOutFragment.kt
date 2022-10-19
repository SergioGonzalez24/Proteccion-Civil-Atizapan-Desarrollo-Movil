package mx.itesm.jmggm.atizapan.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.databinding.FragmentSignOutBinding

class SignOutFragment : Fragment() {
    private lateinit var binding:FragmentSignOutBinding
    var isloged:Boolean=false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentSignOutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarEventos()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=FragmentSignOutBinding.inflate(layoutInflater)
    }
    private fun registrarEventos() {
        binding.btnSignOut.setOnClickListener {
            isloged=false
            val prefs= activity?.getSharedPreferences("logueo", Context.MODE_PRIVATE)
            val editor= prefs?.edit()
            editor?.putBoolean("log",isloged)
            editor?.commit()
            alerta2("Atención.","Cierre de sesión exitoso.","ok")
        }
    }
    private fun alerta2(titulo:String,mensaje:String,button:String){
        val dialog = AlertDialog.Builder(this.requireContext())
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton(button) { view, _ ->
                view.dismiss()

            }
            .setCancelable(false)
            .create()

        dialog.show()

    }
}