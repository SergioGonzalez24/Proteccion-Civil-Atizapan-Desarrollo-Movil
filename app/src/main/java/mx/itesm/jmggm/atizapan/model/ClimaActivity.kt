package mx.itesm.jmggm.atizapan.model

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import mx.itesm.jmggm.atizapan.R
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
/* It fetches weather data from the OpenWeatherMap API and displays it in the UI. */
class ClimaActivity : AppCompatActivity() {

/**
 * It creates a new instance of the WeatherTask class and executes it.
 * 
 * @param savedInstanceState This is the saved state of the activity from the previous instance.
 */
    val CITY: String = "Ciudad López Mateos"
    val API: String = "68b800823cf8c5d32ecea64627e8c994" // Use your own API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)
        weatherTask().execute()

    }

/* The above class is an inner class of the MainActivity class. It extends the AsyncTask class. It
overrides the onPreExecute() method. It makes the ProgressBar visible and the mainContainer and
errorText invisible. */
    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val sys = jsonObj.getJSONObject("sys")
                val wind = jsonObj.getJSONObject("wind")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = "Actualizado el: "+ SimpleDateFormat("dd/MM/yyyy",
                    Locale.US).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val tempMin = "Temp Min: " + main.getString("temp_min")+"°C"
                val tempMax = "Temp Max: " + main.getString("temp_max")+"°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")


                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                val address = jsonObj.getString("name")+", "+sys.getString("country")

                /* Populating extracted data into our views */
                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.updated_at).text =  updatedAtText
                findViewById<TextView>(R.id.status).text = weatherDescription.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                }
                findViewById<TextView>(R.id.temp).text = temp
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.wind).text = windSpeed
                findViewById<TextView>(R.id.pressure).text = pressure
                findViewById<TextView>(R.id.humidity).text = humidity

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }
}
