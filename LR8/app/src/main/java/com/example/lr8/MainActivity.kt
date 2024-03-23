package com.example.lr8

import DatabaseHelper
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.lr8.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var dbHelper: DatabaseHelper
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)

        // Початковий фрагмент - список творів
        replaceFragment(FilterAndListFragment())
    }

    fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragmentContainer, fragment)
        if (isAddToBackStack)
            transaction.addToBackStack(null)

        transaction.commit()
    }
}