package com.example.lr11

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.concurrent.TimeUnit

class TimerService : Service() {

    private var handler: Handler? = null

    private var time: Long = 0

    private var timerThread: Thread? = null

    private var isPaused: Boolean = false

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "pause" -> pauseTimer()
                "resume" -> resumeTimer()
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onBind(intent: Intent): IBinder {
        return LocalBinder()
    }

    override fun onCreate() {
        super.onCreate()
        handler = Handler(Looper.getMainLooper())

        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, IntentFilter().apply {
            addAction("pause")
            addAction("resume")
        })
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        time = intent?.getLongExtra("TIME", 0) ?: 0

        timerThread = Thread(Runnable {
            for (i in time downTo 0) {
                while (isPaused)
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        Log.e("stopTimer", e.message.toString())

                    }

                if (Thread.currentThread().isInterrupted) break
                handler?.post {
                    // Update TextView
                    MainActivity.instance?.updateTextView(i.toString())
                }
                try {
                    TimeUnit.SECONDS.sleep(1)
                } catch (e: InterruptedException) {
                    Log.e("stopTimer", e.message.toString())

                }

            }
            handler?.post {
                Toast.makeText(this, "Timer has finished", Toast.LENGTH_SHORT).show()
            }
        })
        timerThread?.start()

        return START_STICKY
    }

    fun pauseTimer() {
        isPaused = true
    }

    fun resumeTimer() {
        isPaused = false
    }

    fun isTimerRunning(): Boolean {
        return timerThread?.isAlive == true
    }

    override fun onDestroy() {
        timerThread?.interrupt()

        handler?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
