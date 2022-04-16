package com.example.practica

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.CHANNEL_NAME
import com.example.practica.Constants.NOTIFICATION_ID
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.viewModel.ClockViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clockViewModel: ClockViewModel by viewModels()


    override fun onStart() {
        super.onStart()
        clockViewModel.time.observe(this, Observer {
            binding.time.text = it
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.clockviewModel = clockViewModel

        }

    override fun onPause() {
        super.onPause()
        clockViewModel.getNotification()
        clockViewModel.notification?.let { clockViewModel.manager?.notify(NOTIFICATION_ID, it) }
    }

    override fun onRestart() {
        super.onRestart()
        clockViewModel.manager?.cancelAll()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lightColor = Color.BLUE
            enableLights(true)
        }
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

    }

}