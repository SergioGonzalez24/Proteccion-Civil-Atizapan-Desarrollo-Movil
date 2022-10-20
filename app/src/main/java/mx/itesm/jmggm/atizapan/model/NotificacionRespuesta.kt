package mx.itesm.jmggm.atizapan.model

import com.google.gson.annotations.SerializedName

data class NotificacionRespuesta(
    @SerializedName("nombre") var nombre:List<String>,
    @SerializedName("descripcion") var descripcion:List<String>
)
