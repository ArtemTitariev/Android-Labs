package com.example.addphotoapp

import DBHelper
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.io.File
import java.io.IOException
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST_CODE = 1
    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)

        val addButton: Button = findViewById(R.id.btnAdd)
        addButton.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickIntent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            saveImageToDatabase(selectedImageUri)
        }
    }

    private fun saveImageToDatabase(imageUri: Uri?) {
        imageUri?.let {
            try {
                val inputStream = contentResolver.openInputStream(it)
                val imageData = inputStream?.readBytes()
                val photoPath = saveImageToFile(imageData)
                val id = dbHelper.addPhoto(photoPath)
                if (id != -1L) {
                    Toast.makeText(this, "Photo successfully added to the database", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error while saving the photo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Error while saving the photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToFile(imageData: ByteArray?): String {
        val fileName = "${UUID.randomUUID()}.jpg"
        val file = File(filesDir, fileName)
        file.writeBytes(imageData ?: return "")
        return file.absolutePath
    }
}