package com.example.lr11

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.lr11.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var timerService: TimerService? = null

    companion object {
        var instance: MainActivity? = null
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as TimerService.LocalBinder
            timerService = binder.getService()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instance = this

        Intent(this, TimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        binding.btnStart.setOnClickListener {
            if (timerService?.isTimerRunning() == true) {
                Toast.makeText(this, "Timer is already running", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val time = binding.etTimerInput.text.toString().toLongOrNull()
            if (time != null) {
                val intent = Intent(this, TimerService::class.java).apply {
                    putExtra("TIME", time)
                }
                startService(intent)
            }
        }

        binding.btnPause.setOnClickListener {
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("pause"))
            Toast.makeText(this, "Timer has paused", Toast.LENGTH_SHORT).show()
        }

        binding.btnResume.setOnClickListener {
            LocalBroadcastManager.getInstance(this).sendBroadcast(Intent("resume"))
            Toast.makeText(this, "Timer has resumed", Toast.LENGTH_SHORT).show()
        }

        binding.btnStop.setOnClickListener {
            try {
                stopService(Intent(this, TimerService::class.java))
            } catch (e: InterruptedException) {
                Log.e("stopTimer", e.message.toString())
            }
            Toast.makeText(this, "Timer has stopped", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateTextView(time: String) {
        binding.twTimerOutput.text = "Remaining seconds: $time"
    }

    override fun onDestroy() {
        unbindService(connection)
        instance = null

        super.onDestroy()
    }
}