package com.example.aulaite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    companion object {
        private const val KEY_ELEMENT = "element"

        fun newIntent(context: Context, element: String): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(KEY_ELEMENT, element)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        findViewById<TextView>(R.id.text_detail).text = intent.getStringExtra(KEY_ELEMENT)
    }
}
