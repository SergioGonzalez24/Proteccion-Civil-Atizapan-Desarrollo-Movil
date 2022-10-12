package mx.itesm.jmggm.atizapan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import mx.itesm.ag.navegacionstreaming.CostoViewModel
import android.widget.Toast
import mx.itesm.jmggm.atizapan.model.APIService
import mx.itesm.jmggm.atizapan.model.AlertDPO
import mx.itesm.jmggm.atizapan.model.ResponseClass
import okhttp3.OkHttpClient

class AlertMapVM: ViewModel(){
    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://jwtauth-webapi.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val serviceAPI by lazy{
        retrofit.create(mx.itesm.jmggm.atizapan.model.APIService::class.java)
    }

    fun enviarCoordenadas(user_id:Int, directorio_id:Int, order_location:String){
        val obj = AlertDPO(user_id,directorio_id, order_location,)
        serviceAPI.postAlert(obj).enqueue(object: Callback<ResponseClass>{
            override fun onResponse(call:Call<ResponseClass>, response: Response<ResponseClass>){
                 if (response.isSuccessful){
                     println("Coordenadas enviadas correctamente.")
                 }else{
                     println("Error al enviar datos: ${response.message()}")
                 }
            }
            override fun onFailure(call:Call<ResponseClass>,t:Throwable){
                println("Error en el envio de datos: ${t.localizedMessage}")
            }
        })
    }

}