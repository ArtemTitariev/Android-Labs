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

    private var artwork: Artwork? = null

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
            artwork = mainActivity.dbHelper.getArtwork(title, description)!!

            displayData(artwork!!)
        } catch (ex: NullPointerException) {
            Toast.makeText(requireContext(), "Failed to load artwork data!", Toast.LENGTH_LONG).show()
        }

        binding.btnUpdate.setOnClickListener{
            prepareArtworkUpdate()
        }

        binding.btnDelete.setOnClickListener {
            deleteArtwork(artwork!!.id)
        }
    }

    private fun prepareArtworkUpdate() {
        val fragment = UpdateArtworkFragment()

        if (artwork == null) {
            return
        }

        val args = Bundle()
        args.putInt("id", artwork!!.id)
        args.putString("title", artwork!!.title)
        args.putString("description", artwork!!.description)
        fragment.arguments = args

        mainActivity.replaceFragment(fragment)
    }
    private fun deleteArtwork(id: Int) {
        if (! mainActivity.dbHelper.deleteArtwork(id)) {
            Toast.makeText(requireContext(), "Delete failed!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Record deleted", Toast.LENGTH_SHORT).show()
            mainActivity.replaceFragment(FilterAndListFragment())
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
