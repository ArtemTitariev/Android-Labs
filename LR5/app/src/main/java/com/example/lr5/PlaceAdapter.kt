package com.example.lr5

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class PlaceAdapter (activity: Activity, val resourceId: Int, data: List<Place>) :
    ArrayAdapter<Place>(activity, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(resourceId, parent, false)

        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeDescription: TextView = view.findViewById(R.id.placeDescription)

        val place = getItem(position)
        if (place != null) {
            placeName.text = place.name
            placeDescription.text = place.description
        }

        return view
    }
}