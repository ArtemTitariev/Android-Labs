package com.example.lr5

import Place
import com.example.lr5.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr5.databinding.ActivityPlaceListBinding
import com.example.lr5.databinding.PlaceItemBinding


class PlaceListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceListBinding

    private val placeList = ArrayList<Place>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlaces()

        if (!checkPlaces() )
        {
            return
        }

        val layoutManager = LinearLayoutManager(this)
        binding.placeRecyclerView.layoutManager = layoutManager

        val adapter = PlaceAdapter(placeList)
        binding.placeRecyclerView.adapter = adapter
    }

    private fun checkPlaces(): Boolean {
        if (placeList.isEmpty()) {
            val emptyMessage = "List of places is empty!"

            binding.alertTextView.text = emptyMessage
            binding.alertTextView.visibility = View.VISIBLE

            return false
        } else {
            binding.alertTextView.visibility = View.GONE
            return true
        }

    }

    private fun initPlaces() {
        placeList.add(Place("Art Museum", "Unique collection", "Culture Street, 12", 15.0))
        placeList.add(Place("Amusement Park", "Fun for the whole family", "Joy Avenue, 34", 20.0))
        placeList.add(Place("Café 'Aroma'", "Cozy atmosphere", "Aromatic Street, 7", 10.0))
        placeList.add(Place("Theater 'Emotions'", "Captivating performances", "Drama Square, 5", 25.0))
        placeList.add(Place("Summer Concert Hall", "Musical performances", "Harmony Street, 18", 30.0))
        placeList.add(Place("Waterpark 'Waterfall'", "Fun for kids", "Splash Avenue, 42", 18.0))
        placeList.add(Place("Gallery 'Impressions'", "Contemporary art", "Expression Street, 3", 22.0))
        placeList.add(Place("Restaurant 'Delicacies'", "Gastronomic experiences", "Taste Boulevard, 8", 40.0))
        placeList.add(Place("Sports Complex 'Energy'", "Fitness and sports", "Endurance Street, 15", 28.0))
        placeList.add(Place("Beach 'Sunbay'", "Relaxation by the shore", "Sea Breeze Boulevard, 50", 0.0))
    }
}