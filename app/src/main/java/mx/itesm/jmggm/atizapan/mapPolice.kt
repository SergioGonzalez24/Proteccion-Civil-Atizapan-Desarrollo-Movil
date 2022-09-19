package mx.itesm.jmggm.atizapan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.jmggm.atizapan.viewmodel.MapPoliceVM

class mapPolice : Fragment() {

    companion object {
        fun newInstance() = mapPolice()
    }

    private lateinit var viewModel: MapPoliceVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_police, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapPoliceVM::class.java)
        // TODO: Use the ViewModel
    }

}