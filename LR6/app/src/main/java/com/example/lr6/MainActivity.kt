package com.example.lr6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast


import com.example.lr6.databinding.ActivityMainBinding

import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        fun actionStart(context: Context, title: String, content: String) {
            val intent = Intent(context, MainActivity::class.java).apply {
                putExtra("note_title", title)
                putExtra("note_content", content)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val title = intent.getStringExtra("note_title")
//        val content = intent.getStringExtra("note_content")
//        if (title != null && content != null) {
//            var fragment =
//                supportFragmentManager.findFragmentById(R.id.createViewNoteFragment) as ViewNoteFragment
//            fragment.refresh(title, content)
//        }

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