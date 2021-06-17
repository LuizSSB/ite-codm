package com.example.aulaite.intent

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import com.example.aulaite.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_CODE_CAMERA = 363
        const val REQUEST_CODE_GALLERY = 4324

        const val IMAGE_NAME = "imagemSelecionada"
        const val IMAGE_FORMAT = ".jpg"
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
//                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(intent, REQUEST_CODE_GALLERY)
                dialog.cancel()
            }

            dialog.show()
        }

        readBitmap()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK) {
            return
        }

        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                val image = data!!.extras!!.get("data") as Bitmap
                saveBitmap(image)
                readBitmap()
            }
            REQUEST_CODE_GALLERY -> {
                val imageUri = data!!.data!!
                val imageInStream = contentResolver.openInputStream(imageUri)
                val bitmap = BitmapFactory.decodeStream(imageInStream)
                imageInStream?.close()
                saveBitmap(bitmap)
                readBitmap()
            }
        }
    }

    fun readBitmap() {
        val file = File(cacheDir, "$IMAGE_NAME$IMAGE_FORMAT")

        if (!file.exists()) {
            return
        }

        val fileInStream = FileInputStream(file)
        val bitmap = BitmapFactory.decodeStream(fileInStream)
        fileInStream.close()
        findViewById<ImageView>(R.id.image).setImageBitmap(bitmap)
    }

    fun saveBitmap(bitmap: Bitmap) {
        val file = File(cacheDir, "$IMAGE_NAME$IMAGE_FORMAT")
        val fileOutStream = FileOutputStream(file)
        bitmap.compress(
            Bitmap.CompressFormat.JPEG,
            90,
            fileOutStream
        )
        fileOutStream.close()
    }
}
