package mx.itesm.jmggm.atizapan.model



data class alerta(val id: Int?, val order_date: String?, val nombre: String?, val descripcion: String?, val prioridad: String?)

data class alertaResponse(val alertas: List<alerta>)
