package mx.itesm.jmggm.atizapan.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import mx.itesm.jmggm.atizapan.BottomMenu
import mx.itesm.jmggm.atizapan.model.Login.RetroInstance
import mx.itesm.jmggm.atizapan.model.Login.RetroServiceInterface

import mx.itesm.jmggm.atizapan.model.Login.User
import mx.itesm.jmggm.atizapan.model.Login.UserResponse

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
class MainActivityViewModel(): ViewModel() {


    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }

    fun getCreateNewUserObserver(): MutableLiveData<UserResponse?> {
        return createNewUserLiveData
    }





        fun createNewUser(user: User) {
            val retroService  = RetroInstance.getRetroInstance().create(RetroServiceInterface::class.java)
            val call = retroService.createUser(user)
            call.enqueue(object: Callback<UserResponse> {
                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    createNewUserLiveData.postValue(null)
                }

                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if(response.isSuccessful) {
                        createNewUserLiveData.postValue(response.body())
                    } else {
                        createNewUserLiveData.postValue(null)
                    }
                }
            })
        }




}