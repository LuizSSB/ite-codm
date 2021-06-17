package com.example.aulaite.http

import com.example.aulaite.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface DogApi {
    @GET("/api/breed/{breed}/list")
    fun getSubBreeds(
        @Path("breed") mainBreed: String,
        @Query("query") query: String
    ): Call<SubBreedsResponse>

    @GET("https://dog.ceo/api/breed/{breed}/{subBreed}/images/random")
    fun getSubBreedImage(
        @Path("breed") mainBreed: String,
        @Path("subBreed") subBreed: String
    ): Call<SubBreedImageResponse>

    companion object {
        fun newClient(): DogApi {
            val httpClient = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api = retrofit.create(DogApi::class.java)
            return api
        }
    }
}
