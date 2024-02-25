package com.example.lr4

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.example.lr4.databinding.SecondLayoutBinding

    class SecondActivity : AppCompatActivity() {

        private lateinit var binding: SecondLayoutBinding

        private val tag = "--SecondActivity--"

        private lateinit var userName: String

        private lateinit var userSurname: String

        private lateinit var userEmail: String

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = SecondLayoutBinding.inflate(layoutInflater)
            setContentView(R.layout.second_layout)

            userName = intent.getStringExtra("userName").toString()
            userSurname = intent.getStringExtra("userSurname").toString()
            userEmail = intent.getStringExtra("userSurname").toString()

            val textView: TextView = findViewById(R.id.textViewUser)
            textView.text = "${textView.text}$userName $userSurname!"

            val btn: Button = findViewById(R.id.buttonMore)
            btn.setOnClickListener {
                handleButtonMoreClick()
            }

            Log.d(tag, "onCreate")
        }

        private fun handleButtonMoreClick() {
            val intent = Intent(this, UserDetailActivity::class.java)

            intent.putExtra("userName", userName)
            intent.putExtra("userSurname", userSurname)
            intent.putExtra("userEmail", userEmail)

            startActivity(intent)
        }

        override fun onStart() {
            super.onStart()
            Log.d(tag, "onStart")
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
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d(tag, "onDestroy")
        }

        override fun onRestart() {
            super.onRestart()
            Log.d(tag, "onRestart")
        }
    }