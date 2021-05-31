package com.example.aulaite.intent

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import com.example.aulaite.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class ImageActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_CAMERA = 363
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        findViewById<View>(R.id.button_pick_image).setOnClickListener {
            val dialog = BottomSheetDialog(this)
            dialog.setTitle("Pegar foto de onde?")
            dialog.setContentView(R.layout.sheet_image_pick)

            dialog.findViewById<View>(R.id.text_camera)?.setOnClickListener {
                dialog.cancel()

                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, REQUEST_CODE_CAMERA)
            }

            dialog.findViewById<View>(R.id.text_gallery)?.setOnClickListener {
                dialog.cancel()
            }

            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                val image = data!!.extras!!.get("data") as Bitmap
                findViewById<ImageView>(R.id.image).setImageBitmap(image)
            }
        }
    }
}
