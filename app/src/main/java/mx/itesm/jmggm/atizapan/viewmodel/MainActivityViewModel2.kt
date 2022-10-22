package mx.itesm.jmggm.atizapan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.itesm.jmggm.atizapan.model.Login.RetroInstance
import mx.itesm.jmggm.atizapan.model.Login.RetroServiceInterface2

import mx.itesm.jmggm.atizapan.model.Login.UserAdd
import mx.itesm.jmggm.atizapan.model.Login.UserResponseAdd

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author
Sergio Manuel Gonzalez Vargas - A01745446
Gilberto André García Gaytán - A01753176
Jose Miguel Garcia Gurtubay Moreno - A01373750
Josue Bernanrdo Villegas Nuño - A01751694
Fernando Ortiz Saldaña - A01376737
Favio Mariano Dileva Charles - A01745465

 */
class MainActivityViewModel2: ViewModel() {
    var createNewUserLiveData: MutableLiveData<UserResponseAdd?> = MutableLiveData()
    fun getCreateNewUserObserver(): MutableLiveData<UserResponseAdd?> {
        return createNewUserLiveData
    }




    fun createNewUser(user: UserAdd) {
        val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface2::class.java)
        val call = retroService.createUser(user)

        call.enqueue(object: Callback<UserResponseAdd> {
            override fun onFailure(call: Call<UserResponseAdd>, t: Throwable) {
                createNewUserLiveData.postValue(null)
            }

            override fun onResponse(call: Call<UserResponseAdd>, response: Response<UserResponseAdd>) {
                if(response.isSuccessful) {
                    createNewUserLiveData.postValue(response.body())
                } else {
                    createNewUserLiveData.postValue(null)
                }
            }
        })
    }


}



