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

    companion object {
        fun newInstance(title: String, content: String): ViewNoteFragment {
            val fragment = ViewNoteFragment()
            val args = Bundle()
            args.putString("note_title", title)
            args.putString("note_content", content)
            fragment.arguments = args

            return fragment
        }
    }
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

        // Дані з аргументів
        arguments?.let {
            title = it.getString("note_title")
            content = it.getString("note_content")
        }

        refresh()

        // Update
        binding.btnUpdate.setOnClickListener {
            val mainActivity = activity as MainActivity

            mainActivity.openCreateViewActivity(title ?: "", content ?: "")
        }

        // Delete
        binding.btnDelete.setOnClickListener {
            val mainActivity = activity as MainActivity

            mainActivity.deleteNote(MathNote(title.toString(), content.toString()))
        }
    }
    fun refresh() {
        binding.twNoteTitle.text = title
        binding.twNoteContent.text = content
    }
}
