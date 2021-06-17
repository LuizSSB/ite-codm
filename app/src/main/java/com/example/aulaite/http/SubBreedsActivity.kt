package com.example.aulaite.http

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.aulaite.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SubBreedsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_breeds)

        val dogApi = DogApi.newClient()
        val button = findViewById<View>(R.id.button_search)
        val progressBar = findViewById<View>(R.id.progress_loading)
        button.setOnClickListener {
            button.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            val breed = findViewById<EditText>(R.id.edit_breed).text.toString()
            val call = dogApi.getSubBreeds(breed)
            call.enqueue(object : Callback<SubBreedsResponse> {
                override fun onResponse(
                    call: Call<SubBreedsResponse>,
                    response: Response<SubBreedsResponse>
                ) {
                    runOnUiThread {
                        button.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    if (!response.isSuccessful) {
                        return
                    }

                    val body = response.body() ?: return

                    runOnUiThread {
                        findViewById<TextView>(R.id.text_subbreeds).text = body.message.joinToString("\n")
                    }
                }

                override fun onFailure(call: Call<SubBreedsResponse>, t: Throwable) {
                    runOnUiThread {
                        button.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        Toast.makeText(this@SubBreedsActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }
}
