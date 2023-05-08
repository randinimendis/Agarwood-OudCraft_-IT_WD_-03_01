package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinally.databinding.ActivityPayementconfirmationsilpBinding
import com.example.afinally.databinding.ActivityPayementmethodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class payementconfirmationsilp : AppCompatActivity() {
    private lateinit var binding: ActivityPayementconfirmationsilpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayementconfirmationsilpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvTotPrice.text = intent.getStringExtra("totPrice")
        binding.tvCardName.text = intent.getStringExtra("cardName")
        binding.tvCardNo.text = intent.getStringExtra("cardNo")

        binding.okBtn.setOnClickListener {
            intent = Intent(applicationContext, buyiteam::class.java)
            startActivity(intent)

        }


    }
}