package mx.itesm.jmggm.atizapan.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mx.itesm.jmggm.atizapan.R
import mx.itesm.jmggm.atizapan.viewmodel.DirectoryVM

class directory : Fragment() {

    companion object {
        fun newInstance() = directory()
    }

    private lateinit var viewModel: DirectoryVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_directory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DirectoryVM::class.java)
        // TODO: Use the ViewModel
    }

}