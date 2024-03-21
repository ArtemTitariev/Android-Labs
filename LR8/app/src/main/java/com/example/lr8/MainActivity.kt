package com.example.lr8

import DatabaseHelper
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr8.databinding.ActivityMainBinding
import com.example.lr8.models.Artwork


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var db: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.npYear.apply {
            setMaxValue(2100);
            setMinValue(1900)
        }


        val dbHelper = DatabaseHelper(this)
        db = dbHelper.writableDatabase


        val adapter = ArtworkAdapter(dbHelper.getArtworkList())
        binding.rvArtworks.adapter = adapter
        binding.rvArtworks.layoutManager = LinearLayoutManager(this)
    }


}