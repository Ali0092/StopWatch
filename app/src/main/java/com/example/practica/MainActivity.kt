package com.example.practica

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.practica.Constants.CHANNEL_ID
import com.example.practica.Constants.CHANNEL_NAME
import com.example.practica.Constants.NOTIFICATION_ID
import com.example.practica.Constants.REQUEST_CODE
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.service.NotificationService
import com.example.practica.viewModel.ClockViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        startMyService()
    }

    override fun onRestart() {
        super.onRestart()
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
        clockViewModel.time.observe(this, Observer {
            serviceIntent.putExtra("Test", it.toString())
        })
        startForegroundService(serviceIntent)
    }

   private fun stopMyService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        stopService(serviceIntent)
    }

}