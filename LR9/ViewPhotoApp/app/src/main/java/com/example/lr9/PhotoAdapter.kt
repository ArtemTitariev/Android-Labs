package com.example.lr9

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lr9.databinding.DetailFragmentBinding
import com.example.lr9.databinding.ListItemPhotoBinding
import java.io.File

class PhotoAdapter(
    private val context: Context?,
    private val photoList: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = ListItemPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoPath = photoList[position]

        Glide.with(context!!)
            .load(photoPath)
            .fitCenter()
            .into(holder.binding.imageViewPhoto)

        holder.itemView.setOnClickListener {
            onItemClick(photoPath)
        }
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    class PhotoViewHolder(val binding: ListItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
}
