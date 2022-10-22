package mx.itesm.jmggm.atizapan.model

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* It's a class that creates a Retrofit instance with a base URL and a logging interceptor. */
class RetroInstance {
    companion object {

        val BASE_URL = "https://jwtauth-webapi.azurewebsites.net/"

        fun getRetroInstance(): Retrofit {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}