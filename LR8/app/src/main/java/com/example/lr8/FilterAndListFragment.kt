package com.example.lr8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr8.databinding.FilterAndListFragmentBinding

class FilterAndListFragment : Fragment() {

    private lateinit var binding: FilterAndListFragmentBinding

    private lateinit var mainActivity: MainActivity

    private lateinit var adapter: ArtworkAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterAndListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        // Set up RecyclerView
        adapter = getAdapter()

        adapter.setOnItemClickListener { artwork ->
            // Фрагмент з деталями твору
          val artworkDetailFragment = ArtworkDetailFragment()
                .apply {
                arguments = Bundle().apply {
                    putString("title", artwork.title)
                    putString("description", artwork.description)
                }
            }

            mainActivity.replaceFragment(artworkDetailFragment)
        }

        binding.rvArtworks.adapter = adapter
        binding.rvArtworks.layoutManager = LinearLayoutManager(requireContext())

        // Filter button click listener
        binding.btnFilter.setOnClickListener {
            filter()
        }

        binding.btnReset.setOnClickListener {
            resetFilter()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //binding = null
    }

    private fun getAdapter(): ArtworkAdapter {
        return ArtworkAdapter(mainActivity.dbHelper.getArtworkList())
    }

    private fun filter() {
        val genreFilter: String = binding.etGenre.text.toString()
        val yearFilter: Int? = binding.etYear.text.toString().toIntOrNull()

        if (genreFilter.isEmpty() && yearFilter == null) {
            Toast.makeText(requireContext(), "Enter data to filter!", Toast.LENGTH_SHORT).show()
        } else {
            try {
                val filteredList = mainActivity.dbHelper.getFilteredArtworkList(genreFilter, yearFilter)
                adapter.updateList(filteredList)
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), "Not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun resetFilter() {
        adapter = getAdapter()
        binding.rvArtworks.adapter = adapter

        clearInputs()
    }

    private fun clearInputs() {
        binding.etGenre.text.clear()
        binding.etYear.text.clear()
    }
}
