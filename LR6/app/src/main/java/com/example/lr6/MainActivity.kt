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

        const val CREATE_NOTE_FRAGMENT_TAG = "create_note_fragment"
        const val VIEW_NOTE_FRAGMENT_TAG = "view_note_fragment"
        const val CURRENT_FRAGMENT_KEY = "current_fragment"

        const val TITLE_KEY = "note_title"
        const val CONTENT_KEY = "note_content"
        const val MODE_KEY = "is_editing"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(CreateNoteFragment(), CREATE_NOTE_FRAGMENT_TAG)

        binding.btnCreate.setOnClickListener {
            replaceFragment(CreateNoteFragment(), CREATE_NOTE_FRAGMENT_TAG)
        }
    }

    fun replaceFragment(fragment: Fragment, tag: String) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.createViewNoteFragment, fragment, tag)
        transaction.addToBackStack(null)

        transaction.commit()
    }

    fun createNote(note: MathNote) {
        if (!validateNote(note)) return

        val noteListFragment = supportFragmentManager.findFragmentById(R.id.noteListFragment) as? NoteListFragment

        noteListFragment?.getNoteAdapter()?.addNote(note)
        resetFragmentForm()

        showToast("Created!")
    }

    fun editNote(savedNote: MathNote, newNote: MathNote) {
        if (!validateNote(newNote)) return

        val noteListFragment = supportFragmentManager.findFragmentById(R.id.noteListFragment)
                as? NoteListFragment

        var message = ""
        if (noteListFragment?.getNoteAdapter()?.editNote(savedNote, newNote) as Boolean)
        {
            message = "Updated!"
            resetFragmentForm()
        }
        else message = "Update note error!"

        showToast(message)
    }

    fun deleteNote(note: MathNote) {

        val noteListFragment =
            supportFragmentManager.findFragmentById(R.id.noteListFragment) as NoteListFragment

        val message = if (noteListFragment?.getNoteAdapter()?.removeNote(note) as Boolean)
            "Deleted!" else "Delete note error!"

        showToast(message)
        supportFragmentManager.popBackStack()
    }

    private fun validateNote(note: MathNote): Boolean {
        if (note.title.length < 2 || note.content.length < 2) {
            showToast("Title or content is too short!", Toast.LENGTH_LONG)
            return false
        }
        return true
    }

    private fun resetFragmentForm() {
        val fragment =
               supportFragmentManager.findFragmentById(R.id.createViewNoteFragment) as CreateNoteFragment
        fragment.reset()
    }

    fun openCreateViewActivity(title: String, content: String) {
        val fragment = CreateNoteFragment.newInstance(title, content, true)
        replaceFragment(fragment, CREATE_NOTE_FRAGMENT_TAG)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.createViewNoteFragment)
        outState.putString(CURRENT_FRAGMENT_KEY, currentFragment?.tag)

        if (currentFragment?.tag == CREATE_NOTE_FRAGMENT_TAG) {
            val fragment = currentFragment as CreateNoteFragment

            outState.putString(TITLE_KEY, fragment.binding.etNoteTitle.text.toString() ?: "")
            outState.putString(CONTENT_KEY, fragment.binding.etNoteContent.text.toString() ?: "")
            outState.putBoolean(MODE_KEY, fragment.isEditing)

        } else {
            val fragment = currentFragment as ViewNoteFragment

            outState.putString(TITLE_KEY, fragment.binding.twNoteTitle.text.toString() ?: "")
            outState.putString(CONTENT_KEY, fragment.binding.twNoteContent.text.toString() ?: "")
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        when (savedInstanceState.getString(CURRENT_FRAGMENT_KEY)) {
            CREATE_NOTE_FRAGMENT_TAG -> {
                val fragment = CreateNoteFragment.newInstance(
                    savedInstanceState.getString(TITLE_KEY).toString(),
                    savedInstanceState.getString(CONTENT_KEY).toString(),
                    savedInstanceState.getBoolean(MODE_KEY),
                )
                replaceFragment(fragment, CREATE_NOTE_FRAGMENT_TAG)

            }
            VIEW_NOTE_FRAGMENT_TAG -> {

                val fragment = ViewNoteFragment.newInstance(
                    savedInstanceState.getString(TITLE_KEY).toString(),
                    savedInstanceState.getString(CONTENT_KEY).toString()
                )

                replaceFragment(fragment, VIEW_NOTE_FRAGMENT_TAG)
            }
            else -> { // Якщо фрагмент не визначено, показати стандартний
                replaceFragment(CreateNoteFragment(), CREATE_NOTE_FRAGMENT_TAG)
            }
        }
    }

    private fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(
            applicationContext,
            message,
            duration
        ).show()
    }
}