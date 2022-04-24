package com.example.practica.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.*
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.NOTIFICATION_ID
import com.example.practica.Constants.REQUEST_CODE
import com.example.practica.MainActivity
import com.example.practica.R
import com.example.practica.viewModel.ClockViewModel
import kotlinx.coroutines.launch

class NotificationService : LifecycleService() {

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

}


/*   private fun updateMyService(time: String) {
          val notification = getNotification(time)
          val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
          manager.notify(NOTIFICATION_ID, notification.build())
      }
  */
