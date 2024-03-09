package com.example.lr6

import android.os.Bundle
import android.widget.Toast


import com.example.lr6.databinding.ActivityMainBinding

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(CreateNoteFragment())

        
        binding.btnCreate.setOnClickListener {
            replaceFragment(CreateNoteFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.createViewNoteFragment, fragment)
        transaction.addToBackStack(null)

        transaction.commit()
    }
}