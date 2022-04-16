package com.example.practica.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.app.Notification
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.practica.Constants
import com.example.practica.R
import java.text.DateFormat
import java.text.SimpleDateFormat


class ClockViewModel(application: Application) : AndroidViewModel(application) {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private var handler: Handler? = null
    private var flag = false
    private var seconds : Long = 0

    var notification: Notification?=null
    var manager: NotificationManagerCompat?=null

    var play = false
    var pause = false


    fun startTimer() {
        if (!play) {
            handler = Handler(Looper.getMainLooper())
            handler!!.post(object : Runnable {
                override fun run() {
                    if (flag) {
                        handler!!.removeCallbacks(this)
                        flag = false
                    } else {
                        seconds += 100
                        updateTime(seconds)
                        handler!!.postDelayed(this, 100)
                    }
                }
            })
            play = true
            pause = false
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun updateTime(updatedTime: Long) {
        val format: DateFormat = SimpleDateFormat("mm : ss : SS")
        val displayTime: String = format.format(updatedTime)
        _time.value = displayTime
    }

    fun getNotification(){
        notification = NotificationCompat.Builder(context, Constants.CHANNEL_ID)
            .setContentTitle("StopWatch")
            .setContentText(time.value.toString())
            .setSmallIcon(R.drawable.circle)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager = NotificationManagerCompat.from(context)

    }
    fun stopTimer() {
        if (!pause) {
            flag = true
            pause = true
            play = false
        }

    }

    fun resetTimer(){
        seconds=0
        updateTime(seconds)
        stopTimer()
    }

}
