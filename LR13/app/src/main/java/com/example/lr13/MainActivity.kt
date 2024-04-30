package com.example.lr13

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.lr13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ITodoChangeListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var todoList: MutableList<Todo>

    override fun onTodoChange() {
        val todoFragment = supportFragmentManager.findFragmentById(R.id.container) as? TodoFragment
        todoFragment?.setupRecyclerView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Ініціалізація todoList
        todoList = mutableListOf()
        for (i in 1..15) {
            val todo = Todo(i, "Todo $i", if (i % 2 == 0) Todo.TodoStatus.COMPLETED else Todo.TodoStatus.INCOMPLETE)
            todoList.add(todo)
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, TodoFragment())
            .commit()
    }

    // Метод для отримання списку Todo з MainActivity
    fun getTodoList(): MutableList<Todo> = todoList
    fun addTodo(todo: Todo) = todoList.add(todo)
    fun removeTodo(todo: Todo) = todoList.remove(todo)
    fun editTodoStatus(todo: Todo, newStatus: Todo.TodoStatus) {
        val index = todoList.indexOf(todo)
        if (index != -1) {
            val updatedTodo = Todo(todo.id, todo.title, newStatus)
            todoList[index] = updatedTodo
        }
    }



//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//
//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//    }
}