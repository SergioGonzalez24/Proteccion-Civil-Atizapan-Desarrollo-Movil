package mx.itesm.jmggm.atizapan.model.Login
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
 * `User` is a data class that has two properties: `email` and `password`.
 * 
 * `UserResponse` is a data class that has two properties: `estatus` and `user_id`.
 * @property {String?} email - The email address of the user.
 * @property {String?} passsword - The password of the user.
 */
data class User(val email: String?, val passsword: String?)

data class UserResponse(val estatus: String?,val user_id: Int )