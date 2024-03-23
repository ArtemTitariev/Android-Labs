package com.example.lr8

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lr8.databinding.UpdateArtworkFragmentBinding

class UpdateArtworkFragment : Fragment() {

    private lateinit var binding: UpdateArtworkFragmentBinding

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UpdateArtworkFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity = activity as MainActivity

        val id = arguments?.getInt("id")
        val title = arguments?.getString("title")
        val description = arguments?.getString("description")

        binding.etTitle.setText(title)
        binding.etDescription.setText(description)

        binding.btnSave.setOnClickListener{
            updateArtwork(id!!)
        }
    }

    private fun updateArtwork(id: Int) {
        val title = binding.etTitle.text.toString()
        val description = binding.etDescription.text.toString()

        if (! validateInputs(title, description)) return

        //update
        if (! mainActivity.dbHelper.updateArtwork(id, title, description)) {
            Toast.makeText(requireContext(), "Update failed!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Record updated", Toast.LENGTH_SHORT).show()
            mainActivity.replaceFragment(FilterAndListFragment())
        }
    }

    private fun validateInputs(title: String, description: String): Boolean {
        if (title.length < 2) {
            Toast.makeText(requireContext(), "Title is too short!", Toast.LENGTH_LONG).show()
            return false
        } else if (description.length < 10) {
            Toast.makeText(requireContext(), "Description is too short!", Toast.LENGTH_LONG).show()
            return false
        }

        return true
    }
}
