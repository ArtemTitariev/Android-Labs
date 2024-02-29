package com.example.lr5

import Place
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity

class PlaceAdapter(val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeDescription: TextView = view.findViewById(R.id.placeDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.place_item, parent, false)

        // On Click
        val viewHolder = ViewHolder(view)
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            val place = placeList[position]

            val intent = Intent(parent.context, PlaceDetailsActivity::class.java)
            intent.putExtra("place", place)
            parent.context.startActivity(intent)

//            Toast.makeText(
//                parent.context, "you clicked view ${
//                    place.name
//                }",
//                Toast.LENGTH_SHORT
//            ).show()
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeDescription.text = place.description
    }

    override fun getItemCount() = placeList.size
}