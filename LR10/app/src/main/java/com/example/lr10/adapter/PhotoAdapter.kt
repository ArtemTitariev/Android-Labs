package com.example.lr10.adapter
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lr10.R
import java.io.File

class PhotoAdapter(private val photoPaths: List<String>) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoPath = photoPaths[position]
        holder.bind(photoPath)
    }

    override fun getItemCount(): Int {
        return photoPaths.size
    }

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)

        fun bind(photoPath: String) {
            val file = File(photoPath)
            val uri = Uri.fromFile(file)
            // Встановлюємо зображення для ImageView
            Glide.with(itemView.context)
                .load(uri)
                .into(imageView)

            Log.d("--image", "inserted: $photoPath")
        }
    }
}

