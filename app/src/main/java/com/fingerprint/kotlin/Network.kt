package com.fingerprint.kotlin


import android.util.Log
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class Network {
    suspend fun loginUser(email:String,password:String):JsonElement?{
        return try {
            ApiClient.localApiService.loginUser(email,password,
                fingerPrint?.visitorId)
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("", it) }
            null
        }
    }

    suspend fun signUpUser(email:String,password:String,name:String):JsonElement?{
        return try {
            ApiClient.localApiService.signUpUser(email,password,name,
                fingerPrint?.visitorId)
        } catch (e: Exception) {
            e.localizedMessage?.let { Log.e("", it) }
            null
        }
    }

}

object RetrofitInstance {
    private const val LOCAL_BASE_URL = "http://192.168.88.247:8080/user/"

    val localRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl(LOCAL_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun loginUser(@Field("email") email: String,
                  @Field("password") password: String,
                          @Field("visitorId") visitorId: String?,): JsonElement

    @FormUrlEncoded
    @POST("sign-up")
    suspend fun signUpUser(@Field("email") email: String,
                   @Field("password") password: String,
                   @Field("name") name: String,
                           @Field("visitorId") visitorId: String?,): JsonElement
}

object ApiClient {
    val localApiService: ApiService by lazy {
        RetrofitInstance.localRetrofit.create(ApiService::class.java)
    }
}
