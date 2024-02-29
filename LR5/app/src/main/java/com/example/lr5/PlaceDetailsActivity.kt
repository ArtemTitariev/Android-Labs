package com.example.lr5

import Place
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lr5.databinding.ActivityPlaceDetailsBinding

class PlaceDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlaceDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val place: Place? = intent.getParcelableExtra("place")

        binding.placeName.text = place?.name;
        binding.placeDescription.text = "${binding.placeDescription.text} ${place?.description}"
        binding.placeAddress.text = "${binding.placeAddress.text} ${place?.address}"
        binding.placeCostOfVisit.text = "${binding.placeCostOfVisit.text} ${place?.costOfVisit.toString()}"


    }
}