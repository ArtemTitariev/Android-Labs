package com.example.addphotoapp

import DBHelper
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.addphotoapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DBHelper(this)

        binding.btnAdd.setOnClickListener {
            val photoUrl = binding.etPhotoUrl.text.toString()
            if (photoUrl.isNotEmpty()) {
                saveImageToDatabase(photoUrl)
                binding.etPhotoUrl.setText("")
            } else {
                Toast.makeText(this, "Enter a photo URL!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToDatabase(photoUrl: String) {
        val id = dbHelper.addPhoto(photoUrl)
        if (id != -1L) {
            Toast.makeText(this, "Photo successfully added to the database; id = $id", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error while saving the photo", Toast.LENGTH_SHORT).show()
        }
    }
}