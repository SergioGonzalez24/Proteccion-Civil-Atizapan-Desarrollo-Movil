package mx.itesm.jmggm.atizapan.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIWeather {
    @GET("/data/3.0/onecall")
    fun getWeather(@Query("lat")lat:String, @Query("lon")lon:String, @Query("exclude")exclude:String, @Query("appid")APIKey:String):Call<Double>
}
//"https://api.openweathermap.org