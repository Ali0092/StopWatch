package com.example.practica

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.practica.databinding.ActivityMainBinding
import com.example.practica.viewModel.ClockViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val clockViewModel: ClockViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        clockViewModel.time.observe(this, Observer {
            binding.time.text=it
        })
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
           watchFunctions()
        }else{
            with(savedInstanceState){
                binding.time.text=getString("TIME")
            }
           watchFunctions()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("TIME",binding.time.text.toString())

        }
        super.onSaveInstanceState(outState)
    }

    private fun watchFunctions(){
        binding.start.setOnClickListener {
            clockViewModel.startTimer()
            clockViewModel.time.observe(this, Observer { it ->
                binding.time.text = it.toString()
            })
        }
        binding.pause.setOnClickListener {
            clockViewModel.stopTimer()
        }

        binding.restart.setOnClickListener {
            clockViewModel.resetTimer()
        }
    }

}