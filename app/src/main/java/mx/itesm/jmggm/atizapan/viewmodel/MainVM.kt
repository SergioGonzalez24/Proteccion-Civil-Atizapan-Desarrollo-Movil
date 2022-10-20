package mx.itesm.jmggm.atizapan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainVM:ViewModel() {
//BORRAR
    private val client = OkHttpClient.Builder().build()
    val temp=MutableLiveData<Double>()

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://jwtauth-webapi.azurewebsites.net/api/alerta/showall")
            .addConverterFactory(GsonConverterFactory.create())
                //BORRAR CLIENTE
            .client(client)
            .build()
    }

    private val notifAPI by lazy{
        retrofit.create(mx.itesm.jmggm.atizapan.model.APINotif::class.java)
    }

    fun conseguirDatosClima(lat:String,lon:String,exclude:String,api:String){
        //val objQuery = DataQuery(lat,lon,exclude,api)
//        val call =notifAPI.getNotif()
//        val notificaciones=call.body()
//        if(call.isSuccessful){
            //show recycler view
//        }
//        else{
//            //show error
//        }
//    }

}}