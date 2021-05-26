package com.example.aulaite.intent

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
            intent.putExtra(Intent.EXTRA_TEXT, formField.value)
            intent.type = "text/plain"
            startActivity(intent)
        }
    }
}
