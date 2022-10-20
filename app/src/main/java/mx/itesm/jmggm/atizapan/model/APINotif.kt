package mx.itesm.jmggm.atizapan.model

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APINotif {
    @GET
    fun getNotif(@Url url:String):Response<NotificacionRespuesta>
}
//"https://api.openweathermap.org