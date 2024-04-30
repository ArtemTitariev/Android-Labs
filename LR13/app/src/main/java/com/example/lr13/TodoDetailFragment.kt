package com.example.lr13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lr13.databinding.FragmentTodoDetailBinding
import com.google.android.material.snackbar.Snackbar

class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {

    private lateinit var binding: FragmentTodoDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todo = arguments?.getSerializable("todo") as Todo

        binding.tvTitle.text = todo.title
        binding.chkStatus.isChecked = todo.status == Todo.TodoStatus.COMPLETED

        binding.btnDelete.setOnClickListener {
            (requireActivity() as MainActivity).removeTodo(todo)
            Snackbar.make(view, "Todo deleted", Snackbar.LENGTH_SHORT).show()
            todoChanged()
            back()
        }

        binding.chkStatus.setOnCheckedChangeListener { _, isChecked ->
            val newStatus = if (isChecked) Todo.TodoStatus.COMPLETED else Todo.TodoStatus.INCOMPLETE
            (requireActivity() as MainActivity).editTodoStatus(todo, newStatus)
            Snackbar.make(view, "Todo status updated", Snackbar.LENGTH_SHORT).show()
            todoChanged()
        }

        binding.btnBack.setOnClickListener {
            back()
        }
    }

    private fun todoChanged() =  (requireActivity() as MainActivity).onTodoChange()
    // Повернення до попереднього фрагмента
    private fun back() = requireActivity().supportFragmentManager.popBackStack()
}
