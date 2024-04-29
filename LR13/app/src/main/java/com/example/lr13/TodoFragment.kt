package com.example.lr13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lr13.databinding.FragmentTodoBinding
import com.google.android.material.snackbar.Snackbar

class TodoFragment : Fragment() {

    private lateinit var binding : FragmentTodoBinding

    private lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoBinding.inflate(inflater, container, false)

        setupRecyclerView()

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        val todoList = mutableListOf<Todo>()
        for (i in 1..10) {
            val todo = Todo(i,"Todo $i", if (i % 2 == 0) Todo.TodoStatus.COMPLETED else Todo.TodoStatus.INCOMPLETE)
            todoList.add(todo)
        }

        todoAdapter = TodoAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }

        todoAdapter.setTodos(todoList)
    }
}
