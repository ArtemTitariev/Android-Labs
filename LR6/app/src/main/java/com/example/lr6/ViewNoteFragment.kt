package com.example.lr6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lr6.databinding.ViewNoteFragmentBinding

class ViewNoteFragment : Fragment() {

    lateinit var binding: ViewNoteFragmentBinding

    private var title: String? = null
    private var content: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ViewNoteFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Отримайте дані з аргументів
        arguments?.let {
            title = it.getString("note_title")
            content = it.getString("note_content")
        }

        refresh(/*title, content*/)
    }
    fun refresh(/*title: String?, content: String?*/) {
        binding.twNoteTitle.text = title
        binding.twNoteContent.text = content
    }
}
