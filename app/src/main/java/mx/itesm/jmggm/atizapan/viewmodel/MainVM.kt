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

    private val client = OkHttpClient.Builder().build()
    val temp=MutableLiveData<Double>()
    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private val weatherAPI by lazy{
        retrofit.create(mx.itesm.jmggm.atizapan.model.APIWeather::class.java)
    }

    fun conseguirDatosClima(lat:String,lon:String,exclude:String,api:String){
        //val objQuery = DataQuery(lat,lon,exclude,api)
        val call =weatherAPI.getWeather(lat,lon,exclude,api)
        call.enqueue(object: Callback<Double> {
            override fun onResponse(call: Call<Double>, response: Response<Double>){
                if (response.isSuccessful){
                    println("Coordenadas enviadas correctamente.")
                    temp.value=response.body()
                }else{
                    println("Error al enviar datos: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<Double>, t: Throwable) {
                println("Error en el envio de datos: ${t.localizedMessage}")
            }
        })
    }

}