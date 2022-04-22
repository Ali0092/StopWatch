package com.example.practica.service

import android.R.id
import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.practica.Constants
import com.example.practica.Constants.NOTIFICATION_ID
import com.example.practica.MainActivity
import com.example.practica.R


class NotificationService : Service() {

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val pendingIntent = Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, Constants.REQUEST_CODE, notificationIntent, 0)
        }

        val notification: Notification = NotificationCompat.Builder(this, Constants.CHANNEL_ID)
            .setContentTitle("StopWatch")
            .setSmallIcon(R.drawable.clock)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(NOTIFICATION_ID, notification)

        return START_NOT_STICKY
    }



    override fun onDestroy() {
        super.onDestroy()
    }
}