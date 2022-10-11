package mx.itesm.jmggm.atizapan.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST ("https://jwtauth-webapi.azurewebsites.net/api/evento/add")
    fun postAlert(@Body alertDPO:AlertDPO): Call<*>
}
data class AlertDPO(val users_id:Int,val directorio_id:Int,val order_location:String)