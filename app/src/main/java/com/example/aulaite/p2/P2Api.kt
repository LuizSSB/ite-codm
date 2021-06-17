package com.example.aulaite.p2

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface P2Api {
    @Headers("X-API-KEY: 82333f10")
    @GET("/mobiletest.json")
    fun getUsers(@Query("key") key: String): Call<Any>

    companion object {
        fun newClient(): P2Api {
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://my.api.mockaroo.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(P2Api::class.java)
            return api
        }
    }
}
