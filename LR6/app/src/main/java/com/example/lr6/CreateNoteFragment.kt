package com.example.lr6

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lr6.databinding.CreateNoteFragmentBinding


class CreateNoteFragment : Fragment() {

    lateinit var binding: CreateNoteFragmentBinding

    private var isEditing = false

    private lateinit var savedNote: MathNote

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
            isEditing = true

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

            reset()
        }
    }

    fun reset() {
        binding.etNoteTitle.setText("")
        binding.etNoteContent.setText("")
        isEditing = false
    }
}

