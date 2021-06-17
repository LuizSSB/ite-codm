package com.example.aulaite.intent

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.aulaite.BuildConfig
import com.example.aulaite.FormField
import com.example.aulaite.R

class ShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        val formField = findViewById<FormField>(R.id.form_field_text)
        findViewById<View>(R.id.button_share).setOnClickListener {
            // compartilhamento
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_SUBJECT, formField.value)
            intent.putExtra(Intent.EXTRA_TEXT, formField.value)
            intent.type = "text/plain"

            try {
                startActivity(Intent.createChooser(intent, "Escolha um app"))
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(this, "Opa, não consigo abrir o intent", Toast.LENGTH_LONG).show()
            }
        }

        val textUrl = findViewById<EditText>(R.id.text_url)
        findViewById<View>(R.id.button_open).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(textUrl.text.toString())
            startActivity(Intent.createChooser(intent, "Escolha um app"))
        }
    }
}
