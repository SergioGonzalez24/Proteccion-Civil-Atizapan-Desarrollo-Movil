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


class MainActivityViewModel(): ViewModel() {

//    val prefe= getSharedPreferences("matriculas", Context.MODE_PRIVATE)
//    val editor= prefe.edit()
//    editor.putBoolean("mat", alumno?.matricula)
//    editor.commit()
//    activity
//    //Otra act
//    val prefe= getSharedPreferences("matricula", Context.MODE_PRIVATE)
//    m=prefe.getBoolen("mat","-")
//    println("Matricula")


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