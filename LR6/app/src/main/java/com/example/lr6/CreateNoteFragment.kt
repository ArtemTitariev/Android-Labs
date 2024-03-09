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

        val mainActivity = activity as MainActivity
        binding.btnSave.setOnClickListener {
            val transaction = mainActivity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.createViewNoteFragment, ViewNoteFragment())
            transaction.addToBackStack(null)
            transaction.commit()

            //Toast.makeText(mainActivity, "Save note click btn", Toast.LENGTH_SHORT).show()

        }
    }
}

