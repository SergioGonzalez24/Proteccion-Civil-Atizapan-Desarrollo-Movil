package mx.itesm.jmggm.atizapan

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class welcomeLoading : Fragment() {

    companion object {
        fun newInstance() = welcomeLoading()
    }

    private lateinit var viewModel: WelcomeLoadingVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome_loading, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeLoadingVM::class.java)
        // TODO: Use the ViewModel
    }

}