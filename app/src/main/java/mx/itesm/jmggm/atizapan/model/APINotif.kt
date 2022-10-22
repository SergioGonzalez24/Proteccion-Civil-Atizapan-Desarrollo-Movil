package mx.itesm.jmggm.atizapan.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* I'm trying to get a list of notifications from a server, but I'm getting an error when I try to get
the list. */
interface APINotif {
    @GET
    suspend fun getNotif(@Url url:String):Response<NotificacionRespuesta>
}

