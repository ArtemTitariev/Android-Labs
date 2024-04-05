package com.example.lr9

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lr9.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        val rootView = binding.root


        recyclerView = rootView.findViewById(R.id.recycler_view_photos)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Отримання даних з провайдера та їх встановлення в адаптер
        val photoList = getPhotoListFromProvider()

        if (photoList.isEmpty()) {
            Toast.makeText(activity, "No photos are available", Toast.LENGTH_SHORT).show()
        }

        adapter = PhotoAdapter(context, photoList) { photoPath ->
            val detailFragment = DetailFragment.newInstance(photoPath)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, detailFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

        return rootView
    }

    @SuppressLint("Range")
    private fun getPhotoListFromProvider(): List<String> {

        Log.d("--cursor", "START")

        //val contentResolver: ContentResolver = requireContext().contentResolver
        val uri: Uri = Uri.parse("content://com.example.addphotoapp.photoprovider/Photos")

        val projection = arrayOf("photo_path")
        val cursor: Cursor? = requireActivity().contentResolver.query(uri, projection, null, null, null)
        val photoList = mutableListOf<String>()

        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val photoPath = cursor.getString(cursor.getColumnIndex("photo_path"))
                photoList.add(photoPath)
                Log.d("--cursor", photoPath)
            }
        }
        cursor?.close()

        photoList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTr_FsBGtjbkB8UIjf3uIpRdmMky4_yQuUj2w&s")
        photoList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSBx1TOOPnFabU731b6y1wHZCasRwIHfGiJqQ&s")
        photoList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT1eHPKrFrmEX5MnGXylO1AK-t6GRQFiOvhOQ&s")

        return photoList
    }
}
