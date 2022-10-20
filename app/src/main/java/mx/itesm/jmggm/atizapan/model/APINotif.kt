package mx.itesm.jmggm.atizapan.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APINotif {
    @GET
    suspend fun getNotif(@Url url:String):Response<NotificacionRespuesta>
}
// @Url url:String
