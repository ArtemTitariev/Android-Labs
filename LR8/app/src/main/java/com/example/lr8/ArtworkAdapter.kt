package com.example.lr8

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lr8.models.Artwork

class ArtworkAdapter(private var artworkList: List<Artwork>) : RecyclerView.Adapter<ArtworkAdapter.ArtworkViewHolder>() {

    private var itemClickListener: ((Artwork) -> Unit)? = null

    fun setOnItemClickListener(listener: (Artwork) -> Unit) {
        itemClickListener = listener
    }

    inner class ArtworkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
        val genreTextView: TextView = itemView.findViewById(R.id.textViewGenre)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(artworkList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtworkViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_artwork, parent, false)
        return ArtworkViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ArtworkViewHolder, position: Int) {
        val currentItem = artworkList[position]
        holder.titleTextView.text = currentItem.title
        holder.genreTextView.text = currentItem.genre.genre
    }

    override fun getItemCount() = artworkList.size

    fun updateList(newList: List<Artwork>) {
        artworkList = newList
        notifyDataSetChanged()
    }
}
