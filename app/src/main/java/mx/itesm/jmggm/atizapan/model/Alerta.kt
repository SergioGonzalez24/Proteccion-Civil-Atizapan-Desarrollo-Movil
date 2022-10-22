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
 * `alertaResponse` is a type that has a single property, `alertas`, which is a list of `alerta`
 * objects.
 * 
 * `alerta` is a type that has four properties: `id`, `order_date`, `nombre`, and `descripcion`.
 * 
 * `id` and `order_date` are both nullable, meaning they can be null.
 * 
 * `nombre` and `descripcion` are not nullable.
 * 
 * `prioridad` is a nullable string.
 * @property {Int?} id - The id of the alert
 * @property {String?} order_date - The date the order was placed.
 * @property {String?} nombre - The name of the alert
 * @property {String?} descripcion - String?
 * @property {String?} prioridad - String?
 */
data class alerta(val id: Int?, val order_date: String?, val nombre: String?, val descripcion: String?, val prioridad: String?)

data class alertaResponse(val alertas: List<alerta>)
