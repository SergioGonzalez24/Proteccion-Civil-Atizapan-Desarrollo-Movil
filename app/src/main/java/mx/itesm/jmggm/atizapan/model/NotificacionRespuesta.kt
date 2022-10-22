package mx.itesm.jmggm.atizapan.model

import com.google.gson.annotations.SerializedName
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/**
 * NotificacionRespuesta is a data class,
 * it has 2 properties:
 * - nombre which is a List of String
 * - descripcion which is a List of String
 * @property {List<String>} nombre - String
 * @property {List<String>} descripcion - ["descripcion1", "descripcion2", "descripcion3"]
 */
data class NotificacionRespuesta(
    @SerializedName("nombre") var nombre:List<String>,
    @SerializedName("descripcion") var descripcion:List<String>
)
