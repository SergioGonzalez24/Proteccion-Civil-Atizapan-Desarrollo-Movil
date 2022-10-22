package mx.itesm.jmggm.atizapan.model
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
 * ReporteResponse is a data class with 5 properties: id, evento_id, prioridad, estatus, and
 * verificacion.
 * @property {Int?} id - Int?
 * @property {String?} evento_id - The event ID
 * @property {String?} prioridad - String?
 * @property {String?} estatus - String?
 * @property {String?} verificacion - String?
 */
data class ReporteResponse(val id: Int?, val evento_id: String?, val prioridad: String?,
                           var estatus: String?, val verificacion: String?)
