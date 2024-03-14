package com.example.lr7

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class HeadphoneReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_HEADSET_PLUG) {
            val state = intent.getIntExtra("state", -1)
            if (state == 1) {
                Toast.makeText(context, "Headphones connected", Toast.LENGTH_SHORT).show()
            } else if (state == 0) {
                Toast.makeText(context, "Headphones disconnected", Toast.LENGTH_SHORT).show()
            }
        }
    }
}