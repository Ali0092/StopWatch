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
import com.example.practica.Constants.RESETER_STR
import com.example.practica.MainActivity
import com.example.practica.R
import com.example.practica.viewModel.ClockViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotificationService : LifecycleService() {


    companion object {
        private var isServiceRunning = MutableLiveData<Boolean>()
        private var vm = ClockViewModel()
    }

    override fun onCreate() {
        super.onCreate()
            updateMyService(true)

    }


    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning.postValue(false)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        startMyService(RESETER_STR)

        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("ResourceType", "UnspecifiedImmutableFlag")
    fun getNotification(text: String): NotificationCompat.Builder {

        val serviceIntent = PendingIntent.getActivity(
            this,
            REQUEST_CODE,
            Intent(this, MainActivity::class.java),
            0
        )
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("StopWatch")
            .setContentText(text)
            .setSmallIcon(R.drawable.clock)
            .setOnlyAlertOnce(true)
            .setContentIntent(serviceIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

    }

    private fun updateMyService(check: Boolean) {
        if (check) {
            lifecycleScope.launch {
                vm.timer.collect(){
                    val notification = getNotification(it.toString())
                    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    manager.notify(NOTIFICATION_ID, notification.build())
                }
            }
            Log.d("CHECK", "updating.....")
        } else {
            Log.d("CHECK", "Not_updating.....")
        }
    }

    private fun startMyService(text: String) {
        isServiceRunning.postValue(true)
        startForeground(NOTIFICATION_ID, getNotification(text).build())
    }

}