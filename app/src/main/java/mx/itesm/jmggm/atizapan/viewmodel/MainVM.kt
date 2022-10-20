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

class MainVM:ViewModel() {
    val nombreList=MutableLiveData<MutableList<String>>()
    val descriptList=MutableLiveData<MutableList<String>>()


//    private val retrofit by lazy{
//        Retrofit.Builder()
//            .baseUrl("https://jwtauth-webapi.azurewebsites.net/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    private val notifAPI by lazy{
//        retrofit.create(mx.itesm.jmggm.atizapan.model.APINotif::class.java)
//    }
//
//    fun listaNotificaciones(){
//        val call =notifAPI.getNotif("alerta/showall")
//        val notificaciones:NotificacionRespuesta?=call.body()
//
//
//        if(call.isSuccessful){
//            val nombre=notificaciones?.nombre?: emptyList()
//            nombreList.value?.clear()
//            val descripcion=notificaciones?.descripcion?: emptyList()
//            descriptList.value?.clear()
//
//        }
//        else{
//            //show error
//        }
//    }

}