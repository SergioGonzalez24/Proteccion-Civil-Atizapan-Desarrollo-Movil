package mx.itesm.jmggm.atizapan.model.Login


import mx.itesm.jmggm.atizapan.model.Login.UserAdd
import mx.itesm.jmggm.atizapan.model.Login.UserResponseAdd
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RetroServiceInterface2 {

    @POST("api/user/add")
    @Headers("Accept:application/json", "Content-Type:application/json",
    "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJKV1RTZXJ2aWNlQWNjZXNzVG9rZW4iLCJqdGkiOiI0Zjc2NTZjNS0wZWQwLTRlNmYtYjJkOS1lOWMwMjc4NTc0ZTMiLCJpYXQiOiIxMC85LzIwMjIgNzozMjoyOCBBTSIsIklkIjoiMSIsIkRpc3BsYXlOYW1lIjoiRmVybmFuZG8gT3J0aXogU2FsZGHDsWEiLCJFbWFpbCI6ImZlcm5hbmRvb3J0aXNzYWxkYW5hQGljbG91ZC5jb20iLCJleHAiOjE2Njk2MjA3NDgsImlzcyI6IkpXVEF1dGhlbnRpY2F0aW9uU2VydmVyIiwiYXVkIjoiSldUU2VydmljZVBvc3RtYW5DbGllbnQifQ.vDMuYyZHadMpOY5df5V5k9FupbAPHEtXmz_wB4D68p0")
    fun createUser(@Body params: UserAdd): Call<UserResponseAdd>


}