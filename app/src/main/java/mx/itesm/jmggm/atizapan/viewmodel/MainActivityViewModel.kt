package mx.itesm.jmggm.atizapan.viewmodel

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


class MainActivityViewModel(private val state: SavedStateHandle): ViewModel() {
    val _isloged=MutableLiveData<Boolean>(state["Logueado"]?:false)
    val isloged: LiveData<Boolean> get()=_isloged
    lateinit var createNewUserLiveData: MutableLiveData<UserResponse?>
    init {
        createNewUserLiveData = MutableLiveData()
    }
    var isLooged:Boolean=false

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
                        _isloged.value=true
                        state["Logueado"]=_isloged.value
                        println("Prueba youtube ${_isloged}")
                        isLooged=true
                        println(isLooged)
                        createNewUserLiveData.postValue(response.body())
                    } else {
                        createNewUserLiveData.postValue(null)
                    }
                }
            })
        }




}