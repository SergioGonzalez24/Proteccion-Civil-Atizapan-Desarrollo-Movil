package mx.itesm.jmggm.atizapan.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* A data class that is used to send data to the server. */
interface APIService {
    @POST("/api/evento/add")
    fun postAlert(@Body alertDPO:AlertDPO): Call<ResponseClass>
}
data class AlertDPO(val users_id:Int,val directorio_id:Int,val order_location:String)