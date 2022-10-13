package mx.itesm.jmggm.atizapan.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("temp") var temperatura:Double,
)
