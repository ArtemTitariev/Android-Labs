package com.example.lr8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lr8.databinding.ArtworkDetailFragmentBinding
import com.example.lr8.models.Artwork

class ArtworkDetailFragment : Fragment() {

    private lateinit var binding: ArtworkDetailFragmentBinding

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ArtworkDetailFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity


            val title = arguments?.getString("title")!!
            val description = arguments?.getString("description")!!

        try {
            val artwork = mainActivity.dbHelper.getArtwork(title, description)!!

            displayData(artwork)
        } catch (ex: NullPointerException) {
            Toast.makeText(requireContext(), "Failed to load artwork data!", Toast.LENGTH_LONG).show()
        }
    }

    private fun displayData(artwork: Artwork) {
        binding.tvTitle.text = "Title: ${artwork.title}"
        binding.tvDescription.text = "Description: ${artwork.description}"
        binding.tvAuthor.text = "Author: ${artwork.author.firstName} ${artwork.author.lastName}"
        binding.tvYear.text = "Year: ${artwork.year.toString()}"
        binding.tvGenre.text = "Genre: ${artwork.genre.genre}"
    }
}
