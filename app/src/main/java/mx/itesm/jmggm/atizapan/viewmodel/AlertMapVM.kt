package mx.itesm.jmggm.atizapan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import mx.itesm.ag.navegacionstreaming.CostoViewModel
import android.widget.Toast

class AlertMapVM: ViewModel(){

    val alerta=MutableLiveData<String>()

}