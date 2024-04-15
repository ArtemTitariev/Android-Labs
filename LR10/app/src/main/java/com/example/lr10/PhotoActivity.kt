package com.example.lr10

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lr10.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhotoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Отримання шляху до зображення з інтенту
        val imagePath = intent.getStringExtra("image_path")

        // Відображення зображення
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            binding.imageView.setImageBitmap(bitmap)
        }
    }
}
