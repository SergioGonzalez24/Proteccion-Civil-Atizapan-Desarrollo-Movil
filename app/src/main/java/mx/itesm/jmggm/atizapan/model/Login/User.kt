package mx.itesm.jmggm.atizapan.model.Login

//data class User(val users_id: Int?, val directorio_id: Int?, val order_location: String?)
data class User(val email: String?, val passsword: String?)

//data class UserResponse(val id: Int?, val order_date: String?, val users_id: Int?,val directorio_id: Int?,val order_location: String?)
data class UserResponse(val estatus: String?,val user_id: Int )