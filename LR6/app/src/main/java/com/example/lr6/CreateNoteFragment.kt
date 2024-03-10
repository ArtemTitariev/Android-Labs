package com.example.lr6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lr6.databinding.CreateNoteFragmentBinding


class CreateNoteFragment : Fragment() {

    lateinit var binding: CreateNoteFragmentBinding

    var isEditing = false
        private set

    private lateinit var savedNote: MathNote

    companion object {
        fun newInstance(title: String, content: String, isEditing: Boolean = false): CreateNoteFragment {
            val fragment = CreateNoteFragment()
            val args = Bundle()
            args.putString("note_title", title)
            args.putString("note_content", content)
            fragment.arguments = args
            fragment.isEditing = isEditing

            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container:
        ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CreateNoteFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    private fun setupView() {
        val mainActivity = activity as MainActivity

        // Отримуємо дані для редагування (якщо це редагування)
        val title = arguments?.getString("note_title")
        val content = arguments?.getString("note_content")

        // Якщо title та content не null, то це редагування
        if (title != null && content != null) {
            savedNote = MathNote(title, content)

            binding.etNoteTitle.setText(title)
            binding.etNoteContent.setText(content)
        }

        binding.btnSave.setOnClickListener {
            val newNote = MathNote(
                binding.etNoteTitle.text.toString(),
                binding.etNoteContent.text.toString()
            )

            if (isEditing) {
                mainActivity.editNote(savedNote, newNote)
            } else {
                mainActivity.createNote(newNote)
            }
        }
    }

    fun reset() {
        binding.etNoteTitle.setText("")
        binding.etNoteContent.setText("")
        isEditing = false
    }
}
