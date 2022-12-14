package mx.itesm.jmggm.atizapan.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.Toast
import mx.itesm.jmggm.atizapan.model.APIService
import mx.itesm.jmggm.atizapan.model.AlertDPO
import mx.itesm.jmggm.atizapan.model.ReporteResponse
import mx.itesm.jmggm.atizapan.model.ResponseClass
import mx.itesm.jmggm.atizapan.view.MainActivity.Companion.id_reporte
import okhttp3.OkHttpClient
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* It sends a POST request to the server with the user's location and returns a response with the ID of
the report. */
class AlertMapVM: ViewModel(){
    private val client = OkHttpClient.Builder().build()
    val respuesta = mutableListOf<ResponseClass>()
    val objrespuesta=MutableLiveData<MutableList<ResponseClass>>()
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
        val call = serviceAPI.postAlert(obj)
        var respuesta2:ResponseClass
        call.enqueue(object: Callback<ResponseClass>{
            override fun onResponse(call:Call<ResponseClass>, response: Response<ResponseClass>){
                 if (response.isSuccessful){
                     respuesta2 = response.body()!!
                     id_reporte.add(respuesta2)
                     respuesta.add(respuesta2)
                     objrespuesta.value=respuesta
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