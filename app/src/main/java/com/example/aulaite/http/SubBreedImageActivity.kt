package com.example.aulaite.http

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.aulaite.R
import com.example.aulaite.p2.P2Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubBreedImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_breed_image)

        P2Api.newClient().getUsers("82333f10").enqueue(object : Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                println("RESPOSTA ${response.code()} ${response.body()}")
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                t.printStackTrace()
            }
        })

        val api = DogApi.newClient()

        findViewById<View>(R.id.button_load).setOnClickListener {
            val breed = findViewById<EditText>(R.id.edit_breed).text.toString()
            val subBreed = findViewById<EditText>(R.id.edit_subbreed).text.toString()
            api.getSubBreedImage(breed, subBreed).enqueue(object : Callback<SubBreedImageResponse> {
                override fun onResponse(
                    call: Call<SubBreedImageResponse>,
                    response: Response<SubBreedImageResponse>
                ) {
                    val body = response.body() ?: return
                    val imageUrl = body.message

                    val imageView = findViewById<ImageView>(R.id.image_subbreed)
                    Glide.with(imageView)
                        .load(imageUrl)
                        .placeholder(R.drawable.login)
                        .into(imageView)
                }

                override fun onFailure(call: Call<SubBreedImageResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
    }
}
