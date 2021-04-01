package com.example.aulaite

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

fun String.foobar(): String {
    return "foobar $this"
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("barbaz".foobar()) // foobar barbaz

        "asdas".let { value -> println(value) } // asdas

        fun doNumberOperation(op: (Float, Float) -> Float) {
//            val editNumber1 = findViewById<EditText>(R.id.edit_number1)
//            val number1Str = editNumber1.text.toString()
//            val number1 = number1Str.toFloatOrNull()
//
//            val number2 = edit_number2.text.toString().toFloatOrNull()
//
//            if (number1 != null && number2 != null) {
//                val result = try {
//                    op(number1, number2).toString()
//                } catch(ex: Exception) {
//                    ex.message
//                }
//                text_result.text = result
//            } else {
//                Toast.makeText(this, "MEW DIGITA OS NUMERO AI VALEW", Toast.LENGTH_SHORT).show()
//            }
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

    private fun checkOutResources() {
        val appName = this.getString(R.string.app_name) // Aula ITE
        val helloLuiz = getString(R.string.label_hello_word, "Luiz") // Olá Luiz!
        val hello = getString(R.string.label_hello_word) // Olá %s!
        val color = ContextCompat.getColor(this, R.color.colorPrimary)

        val stringArray = resources.getStringArray(R.array.options_signup_username) // [solteirolegal, casalcurioso, cachorros_da_trevas]
        val quantityProducts = resources.getQuantityString(
            R.plurals.quantity_cart_products,
            3,
            3
        ) // Você selecionou 3 produtos

        val marginDefault = resources.getDimensionPixelSize(R.dimen.margin_default)
        val intArray = resources.getIntArray(R.array.array_integer_general)
        val boolVal = resources.getBoolean(R.bool.control_main_open_profile)

        val view = layoutInflater.inflate(R.layout.activity_main, null, false)
        val drawable = ContextCompat.getDrawable(this, R.drawable.ic_launcher_background)
        val mipmap = ContextCompat.getDrawable(this, R.mipmap.ic_launcher)
    }

    // Float -> Float?

    // ImageButton -> Button -> View
    // val imgBtn: ImageButton = ...
    // val view: View = imgBtn
    // val vie2: EditText = imgBtn // error
}
