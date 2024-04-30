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

    fun setupRecyclerView() {
        val todoList = (requireActivity() as MainActivity).getTodoList()

        todoAdapter = TodoAdapter(requireActivity().supportFragmentManager)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = todoAdapter
        }

        todoAdapter.setTodos(todoList)
    }
}
