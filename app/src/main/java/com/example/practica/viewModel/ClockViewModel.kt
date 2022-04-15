package com.example.practica.viewModel

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class ClockViewModel : ViewModel() {

    private val _time = MutableLiveData<String>()
    val time: LiveData<String>
        get() = _time

    private var handler: Handler? = null
    private var flag = false
    private var seconds : Long = 0

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

    @SuppressLint("SimpleDateFormat")
    private fun updateTime(updatedTime: Long) {
        val format: DateFormat = SimpleDateFormat("mm : ss : SS")
        val displayTime: String = format.format(updatedTime)
        _time.value = displayTime
    }

}
