package com.example.aulaite

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView

class FormField : FrameLayout {
    constructor(context: Context) : super(context) {
        setup(context, null)
    }
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setup(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attributeSet: AttributeSet?) {
        LayoutInflater.from(context)
            .inflate(R.layout.view_form_field, this, true)

        if (attributeSet == null) {
            return
        }

        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.FormField)

        label = attrs.getString(R.styleable.FormField_label) ?: ""
        value = attrs.getString(R.styleable.FormField_value) ?: ""
        hint = attrs.getString(R.styleable.FormField_android_hint) ?: ""

        val inputTypeAttrValue = attrs.getInt(R.styleable.FormField_inputType, 0)
        inputType = FormInputType.values()[inputTypeAttrValue]

        attrs.recycle()
    }

    private val labelView get() =
        findViewById<TextView>(R.id.text_form_field)

    var label: String
        get() = labelView.text.toString()
        set(value) {
            labelView.text = value
        }

    private val textField: EditText get() = findViewById(R.id.edit_form_field)

    var hint: String
        get() = textField.hint.toString()
        set(value) {
            textField.hint = value
        }

    var inputType: FormInputType
        get() = when(textField.inputType) {
            FormInputType.Numeric.editTextInputType -> FormInputType.Numeric
            FormInputType.Phone.editTextInputType -> FormInputType.Phone
            else -> FormInputType.Text
        }
        set(value) {
            textField.inputType = value.editTextInputType
        }

    var value: String
        get() = textField.text.toString()
        set(value) = textField.setText(value)

    enum class FormInputType(val editTextInputType: Int) {
        Text(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL),
        Numeric(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL),
        Phone(InputType.TYPE_CLASS_PHONE)
    }
}
