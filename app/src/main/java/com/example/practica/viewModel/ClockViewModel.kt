package com.example.practica.viewModel


import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.example.practica.Constants.RESETER_STR
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import java.text.DateFormat
import java.text.SimpleDateFormat

class ClockViewModel : ViewModel(), LifecycleObserver {

    var play = false
    var pause = false
    var reset = false
    var seconds = 0

    @SuppressLint("SimpleDateFormat")
    val timer = flow<String> {
            while (true) {

                delay(100L)
                if(reset){
                    emit(RESETER_STR)
                    seconds=0
                    break
                }
                else if(pause){
                    break
                }
                else
                seconds += 100
                val format: DateFormat = SimpleDateFormat("mm : ss : SS")
                val displayTime: String = format.format(seconds)
                emit(displayTime)
            }


    }

     fun getPlayStatus(p: Boolean) {
        Log.d("status..","Playing..")
        play = true
        pause = false
        reset = false
    }

     fun getPauseStatus(p: Boolean) {
        Log.d("status..","Pausing..")
        play = false
        pause = true
        reset = false
    }

     fun getResetStatus(r: Boolean) {
        Log.d("status..","Reseting..")
        play = false
        pause = false
        reset = true
    }


}
