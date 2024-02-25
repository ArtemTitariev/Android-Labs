package com.example.lr4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.lr4.databinding.UserDetailLayoutBinding

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: UserDetailLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UserDetailLayoutBinding.inflate(layoutInflater)
        setContentView(R.layout.user_detail_layout)


        val userName: String = intent.getStringExtra("userName").toString()
        val userSurname: String = intent.getStringExtra("userSurname").toString()
        val userEmail: String = intent.getStringExtra("userEmail").toString()

        val textView: TextView = findViewById(R.id.textViewUserDetails)
        textView.text = "Name: $userName\nSurname: $userSurname\nEmail: $userEmail"
    }
}