package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinally.databinding.ActivityBuyandsellBinding

class buyandsell : AppCompatActivity() {
    private lateinit var binding: ActivityBuyandsellBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyandsellBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buyBtn.setOnClickListener {
            intent = Intent(applicationContext, buyiteam::class.java)
            startActivity(intent)

        }

        binding.sellBtn.setOnClickListener {
            intent = Intent(applicationContext, sells_iteam::class.java)
            startActivity(intent)

        }

    }
}