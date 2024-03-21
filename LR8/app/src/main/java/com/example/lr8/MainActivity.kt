package com.example.lr8

import DatabaseHelper
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr8.databinding.ActivityMainBinding
import com.example.lr8.models.Artwork


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        db = dbHelper.writableDatabase

        setupActivity()
    }

    private fun setupActivity() {

        val adapter = ArtworkAdapter(dbHelper.getArtworkList())
        binding.rvArtworks.adapter = adapter
        binding.rvArtworks.layoutManager = LinearLayoutManager(this)

        // Filter listener
        binding.btnFilter.setOnClickListener {
            val genreFilter: String = binding.etGenre.text.toString()
            val yearFilter: Int? =  if (binding.etYear.text.toString() != "")
                binding.etYear.text.toString().toInt()
            else null

            if (genreFilter == "" && yearFilter == null) {
                Toast.makeText(this, "Enter data to filter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val filteredList = dbHelper.getFilteredArtworkList(genreFilter, yearFilter)
                adapter.updateList(filteredList)
            }
            catch (e: NullPointerException) {
                Toast.makeText(this, "Not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }



}