package com.example.lr13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lr13.databinding.FragmentCreateTodoBinding
import com.google.android.material.snackbar.Snackbar

class CreateTodoFragment : Fragment() {

    private lateinit var binding: FragmentCreateTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCreate.setOnClickListener {
            val title = binding.etTitle.text.toString()
            if (title.length < 2) {
                Snackbar.make(view, "Todo title is too short!", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val status = if (binding.chkStatus.isChecked) Todo.TodoStatus.COMPLETED else Todo.TodoStatus.INCOMPLETE
            val todo = Todo(title, status)

            (requireActivity() as MainActivity).addTodo(todo)

            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
