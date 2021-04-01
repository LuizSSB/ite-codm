package com.example.aulaite

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView


class FormField : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
//    constructor(
//        context: Context,
//        attrs: AttributeSet?,
//        defStyleAttr: Int,
//        defStyleRes: Int
//    ) : super(context, attrs, defStyleAttr, defStyleRes)
    init {
        LayoutInflater.from(context)
            .inflate(R.layout.view_form_field, this, true)
    }

    val label: TextView get() = findViewById(R.id.text_form_field)

    val textField: EditText get() = findViewById(R.id.edit_form_field)
}
