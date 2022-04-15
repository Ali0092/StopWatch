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

    private var handler:Handler?=null
    private lateinit var countDownTimer: CountDownTimer
    private var flag=false
    var seconds:Long=0

    init {
        _time.value = "00:00"
    }
    fun startTimer() {

        handler = Handler(Looper.getMainLooper())
        handler!!.post(object : Runnable {
            override fun run() {
                if(flag){
                    handler!!.removeCallbacks(this)
                    flag=false
                }
                else {
                    seconds += 100
                    updateTime(seconds)
                    handler!!.postDelayed(this, 100)
                }
            }
        })
    }

    fun stopTimer() {
        flag=true
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateTime(updatedTime: Long) {
        val format: DateFormat = SimpleDateFormat("mm : ss : SS")
        val displayTime: String = format.format(updatedTime)
       _time.value=displayTime
    }

}
