package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinally.databinding.ActivityDashboardBinding

class dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scheduleBtn.setOnClickListener {
            intent = Intent(applicationContext, viewschdul::class.java)
            startActivity(intent)

        }
        binding.buysellBtn.setOnClickListener {
            intent = Intent(applicationContext, buyandsell::class.java)
            startActivity(intent)

        }
        binding.aboutUsBtn.setOnClickListener {
            intent = Intent(applicationContext, aboutus::class.java)
            startActivity(intent)

        }
        binding.newsBtn.setOnClickListener {
            intent = Intent(applicationContext, newspage::class.java)
            startActivity(intent)

        }
        binding.comBtn.setOnClickListener {
            intent = Intent(applicationContext, community::class.java)
            startActivity(intent)

        }
        binding.profileBtn.setOnClickListener {
            intent = Intent(applicationContext, profile::class.java)
            startActivity(intent)

        }
    }
}