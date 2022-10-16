package mx.itesm.jmggm.atizapan.model.Login

//data class User(val users_id: Int?, val directorio_id: Int?, val order_location: String?)
data class UserAdd(val email: String?, val fullName: String?, val passsword: String?, val colonia: String?,val telefono: String?)

//data class UserResponse(val id: Int?, val order_date: String?, val users_id: Int?,val directorio_id: Int?,val order_location: String?)
data class UserResponseAdd(val estatus: String?)


