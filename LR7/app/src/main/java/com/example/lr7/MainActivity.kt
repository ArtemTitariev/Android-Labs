package com.example.lr7

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity

import com.example.lr7.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val headphoneReceiver = HeadphoneReceiver()

    private var isHeadphoneConnected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switch = binding.swEnableHeadphoneBroadcast;
        switch.setOnCheckedChangeListener { _, isChecked ->
            isHeadphoneConnected = isChecked
            if (isChecked) {
                registerHeadphoneReceiver()
            } else {
                unregisterHeadphoneReceiver()
            }
        }
    }

    private fun registerHeadphoneReceiver() {
        val filter = IntentFilter(Intent.ACTION_HEADSET_PLUG)
        registerReceiver(headphoneReceiver, filter)
    }

    private fun unregisterHeadphoneReceiver() {
        unregisterReceiver(headphoneReceiver)
    }

}

