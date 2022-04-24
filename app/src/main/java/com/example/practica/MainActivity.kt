package com.example.practica

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.CHANNEL_NAME
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.service.NotificationService
import com.example.practica.viewModel.ClockViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clockViewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()
        binding.clockviewModel = clockViewModel

       clockViewModel.time.observe(this, Observer {
           binding.time.text=it.toString()
       })
    }

    override fun onPause() {
        super.onPause()
        clockViewModel.time.observeForever{
            startMyService(it.toString())
        }
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


    private fun startMyService(time:String) {
        val serviceIntent = Intent(this, NotificationService::class.java)
        serviceIntent.putExtra("timer_data",time)
        startForegroundService(serviceIntent)
    }

   private fun stopMyService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
       stopService(serviceIntent)
    }

    override fun onRestart() {
        super.onRestart()
        stopMyService()
    }

    override fun onStop() {
        super.onStop()
        stopMyService()
    }
    override fun onDestroy() {
        super.onDestroy()
        stopMyService()
    }
}