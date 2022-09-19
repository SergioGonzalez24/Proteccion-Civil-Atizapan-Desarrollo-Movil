package mx.itesm.jmggm.atizapan.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.viewmodel.MapRedCrossViewModel

class mapRedCross : Fragment() {

    companion object {
        fun newInstance() = mapRedCross()
    }

    private lateinit var viewModel: MapRedCrossViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map_red_cross, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MapRedCrossViewModel::class.java)
        // TODO: Use the ViewModel
    }

}