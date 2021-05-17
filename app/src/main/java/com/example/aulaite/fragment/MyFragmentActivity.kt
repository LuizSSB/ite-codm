package com.example.aulaite.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.aulaite.R

class MyFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_fragment)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame_fragment, TextFragment.newInstance("foo bar"), "textor")
        transaction.commit()

        var count = 0

        findViewById<View>(R.id.button_add).setOnClickListener {
            count += 1
            val newTransaction = supportFragmentManager.beginTransaction()
            newTransaction.replace(R.id.frame_fragment, TextFragment.newInstance("Contador $count"), "textor")
                .addToBackStack("textor")
            newTransaction.commit()
        }
    }
}
