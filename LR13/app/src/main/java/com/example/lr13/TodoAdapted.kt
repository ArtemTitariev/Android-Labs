package com.example.lr13

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val fragmentManager: FragmentManager) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todoList: MutableList<Todo> = mutableListOf()

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val statusImageView: ImageView = itemView.findViewById(R.id.statusImageView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val todo = todoList[position]
                    val bundle = Bundle().apply {
                        putSerializable("todo", todo)
                    }
                    val todoDetailFragment = TodoDetailFragment()
                    todoDetailFragment.arguments = bundle
                    fragmentManager.beginTransaction()
                        .replace(R.id.container, todoDetailFragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentItem = todoList[position]
        holder.titleTextView.text = currentItem.title
        holder.statusImageView.setImageResource(
            when (currentItem.status) {
                Todo.TodoStatus.COMPLETED -> R.drawable.ic_completed
                Todo.TodoStatus.INCOMPLETE -> R.drawable.ic_incomplete
            }
        )
    }

    override fun getItemCount() = todoList.size

    fun setTodos(list: List<Todo>) {
        todoList.clear()
        todoList.addAll(list)
        notifyDataSetChanged()
    }
}