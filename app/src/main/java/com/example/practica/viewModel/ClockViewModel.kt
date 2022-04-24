package com.example.practica.viewModel

import android.annotation.SuppressLint
import android.app.Application
import android.app.Notification
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import com.example.practica.Constants
import com.example.practica.R
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.FusibleFlow
import kotlinx.coroutines.launch

import java.text.DateFormat
import java.text.SimpleDateFormat


class ClockViewModel : ViewModel(), LifecycleObserver {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    @SuppressLint("StaticFieldLeak")
    private var handler: Handler? = null
    private var flag = false
    private var seconds: Long = 0
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

    fun stopTimer() {
        if (!pause) {
            flag = true
            pause = true
            play = false
        }

    }

    fun resetTimer() {
        seconds = 0
        updateTime(seconds)
        stopTimer()
    }

}
