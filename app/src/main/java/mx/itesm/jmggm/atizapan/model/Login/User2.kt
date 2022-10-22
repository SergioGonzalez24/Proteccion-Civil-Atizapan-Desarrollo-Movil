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
 * UserAdd is a data class that has 4 properties: email, fullName, password, colonia, telefono.
 * 
 * UserResponseAdd is a data class that has 1 property: estatus.
 * @property {String?} email - The email address of the user.
 * @property {String?} fullName - The user's full name.
 * @property {String?} passsword - String?
 * @property {String?} colonia - neighborhood
 * @property {String?} telefono - String?
 */
data class UserAdd(val email: String?, val fullName: String?, val passsword: String?, val colonia: String?,val telefono: String?)


data class UserResponseAdd(val estatus: String?)


