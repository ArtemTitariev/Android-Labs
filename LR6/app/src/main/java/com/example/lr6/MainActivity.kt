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

    fun createNote(note: MathNote) {
        if (!validateNote(note)) return

        val noteListFragment = supportFragmentManager.findFragmentById(R.id.noteListFragment) as? NoteListFragment

        noteListFragment?.getNoteAdapter()?.addNote(note)
        clearForm()

        //supportFragmentManager.popBackStack()
    }

    fun editNote(savedNote: MathNote, newNote: MathNote) {
        if (!validateNote(newNote)) return

        val noteListFragment = supportFragmentManager.findFragmentById(R.id.noteListFragment) as? NoteListFragment

        noteListFragment?.getNoteAdapter()?.editNote(savedNote, newNote)
        clearForm()

        //supportFragmentManager.popBackStack()
    }

    fun validateNote(note: MathNote): Boolean {
        if (note.title.length < 2 || note.content.length < 2) {
            Toast.makeText(
                applicationContext,
                "Title or content is too short!",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    fun clearForm() {
        val fragment =
               supportFragmentManager.findFragmentById(R.id.createViewNoteFragment) as CreateNoteFragment
        fragment.reset()
    }

    fun openCreateViewActivity(title: String?, content: String?) {
        val fragment = CreateNoteFragment()

        val bundle = Bundle().apply {
            putString("note_title", title)
            putString("note_content", content)
        }

        fragment.arguments = bundle

        replaceFragment(fragment)
    }
}