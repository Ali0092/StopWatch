package com.example.practica.service

import android.annotation.SuppressLint
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.NOTIFICATION_ID
import com.example.practica.R

class NotificationService : LifecycleService() {

    override fun onCreate() {
        super.onCreate()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val data = intent?.getStringExtra("timer_data")
        startMyService(data!!)
        return START_STICKY
    }

    @SuppressLint("ResourceType")
    fun getNotification(text: String): NotificationCompat.Builder {

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("StopWatch")
            .setContentText(text)
            .setSmallIcon(R.drawable.clock)
            .setOnlyAlertOnce(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    }

    private fun startMyService(text: String) {
        startForeground(NOTIFICATION_ID, getNotification(text).build())
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
    }
}


/*   private fun updateMyService(time: String) {
          val notification = getNotification(time)
          val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
          manager.notify(NOTIFICATION_ID, notification.build())
      }
  */
