package com.example.aulaite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun doNumberOperation(op: (Float, Float) -> Float) {
            val editNumber1 = findViewById<EditText>(R.id.edit_number1)
            val number1Str = editNumber1.text.toString()
            val number1 = number1Str.toFloatOrNull()

            val number2 = edit_number2.text.toString().toFloatOrNull()

            if (number1 != null && number2 != null) {
                val result = try {
                    op(number1, number2).toString()
                } catch(ex: Exception) {
                    ex.message
                }
                text_result.text = result
            } else {
                Toast.makeText(this, "MEW DIGITA OS NUMERO AI VALEW", Toast.LENGTH_SHORT).show()
            }
        }

        fun handleOperationClick(op: (Float, Float) -> Float): (v: Any) -> Unit {
            return {
                doNumberOperation(op)
            }
        }

        val buttonAdd = findViewById<View>(R.id.button_add)
        buttonAdd.setOnClickListener(handleOperationClick { number1, number2 -> number1 + number2 })

        val buttonSubtract = findViewById<View>(R.id.button_subtract)
        buttonSubtract.setOnClickListener(handleOperationClick { number1, number2 -> number1 - number2 })

        button_multiply.setOnClickListener(handleOperationClick { a, b -> a * b })

        button_divide.setOnClickListener(handleOperationClick { a, b -> a / b })
    }

    // Float -> Float?

    // ImageButton -> Button -> View
    // val imgBtn: ImageButton = ...
    // val view: View = imgBtn
    // val vie2: EditText = imgBtn // error
}
