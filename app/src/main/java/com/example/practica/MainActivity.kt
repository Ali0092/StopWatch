package com.example.practica

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.viewModel.ClockViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clockViewModel: ClockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var play = false
        var pause = false

        binding.start.setOnClickListener {
            if (!play) {
                clockViewModel.startTimer()
                clockViewModel.time.observe(this, Observer { it ->
                    binding.time.text = it.toString()
                })
                play = true
                pause = false
            }
        }
        binding.pause.setOnClickListener {
            if (!pause) {
                clockViewModel.stopTimer()
                pause = true
                play = false
            }
        }

    }

}