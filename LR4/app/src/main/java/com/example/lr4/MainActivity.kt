package com.example.lr4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity


import com.example.lr4.databinding.MainLayoutBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainLayoutBinding

    private val tag = "--MainActivity--"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

       setSupportActionBar(binding.toolbar)

        binding.buttonSubmit.setOnClickListener {
            handleButtonSubmitClick()
        }

        Log.d(tag, "onCreate")
    }

    private fun handleButtonSubmitClick() {
        val userName: String = binding.userName.text.toString()
        val userSurname: String = binding.userSurname.text.toString()
        val userEmail: String = binding.userEmail.text.toString()

        if (validateInputs(userName, userSurname, userEmail)) {
            val intent = Intent(this, SecondActivity::class.java)

            intent.putExtra("userName", userName)
            intent.putExtra("userSurname", userSurname)
            intent.putExtra("userEmail", userEmail)

            startActivity(intent)
        }
    }

    private fun validateInputs(userName: String, userSurname: String, userEmail: String): Boolean {
        return validateName(userName, "Name")
                && validateName(userSurname, "Second name")
                && validateEmail(userEmail)
    }

    private fun validateName(name: String, field: String): Boolean {
        if (name.length < 2 || name.length > 20) {
            Toast.makeText(this, "$field must be from 2 to 20 characters", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        if (!email.matches(emailRegex.toRegex())) {
            Toast.makeText(this, "Incorrect email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.instr_item -> {
                val intent = Intent(this, HelpActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
        Toast.makeText(this, "$tag onStart method call", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
        Toast.makeText(this, "$tag onStop method call", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
        Toast.makeText(this, "$tag onRestart method call", Toast.LENGTH_SHORT).show()
    }

}

