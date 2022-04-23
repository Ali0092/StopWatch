package com.example.practica

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.CHANNEL_NAME
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.service.NotificationService
import com.example.practica.viewModel.ClockViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clockViewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        binding.clockviewModel = clockViewModel


        binding.start.setOnClickListener {
            clockViewModel.getPlayStatus(true)
            lifecycleScope.launch {
                clockViewModel.timer.collect {
                    binding.time.text = it.toString()
                }
            }
        }

        binding.pause.setOnClickListener {
            clockViewModel.getPauseStatus(true)
        }

        binding.restart.setOnClickListener {
            clockViewModel.getResetStatus(true)
        }

    }

    override fun onPause() {
        super.onPause()
        startMyService()
    }
    override fun onRestart() {
        super.onRestart()
        clockViewModel.getResetStatus(true)
        stopMyService()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopMyService()
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

    private fun startMyService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        startForegroundService(serviceIntent)
    }

    private fun stopMyService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        stopService(serviceIntent)
    }

}