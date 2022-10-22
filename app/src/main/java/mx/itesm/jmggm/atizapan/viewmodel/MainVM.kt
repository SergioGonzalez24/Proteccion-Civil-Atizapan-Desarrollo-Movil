package mx.itesm.jmggm.atizapan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mx.itesm.jmggm.atizapan.databinding.FragmentMainBinding
import mx.itesm.jmggm.atizapan.model.NotifAdapter
import mx.itesm.jmggm.atizapan.model.NotificacionRespuesta
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/* A class that extends ViewModel and has two MutableLiveData variables. */
class MainVM:ViewModel() {
    val nombreList=MutableLiveData<MutableList<String>>()
    val descriptList=MutableLiveData<MutableList<String>>()

}