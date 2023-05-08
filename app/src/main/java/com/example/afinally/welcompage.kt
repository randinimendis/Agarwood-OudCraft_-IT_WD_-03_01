package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinally.databinding.ActivityWelcompageBinding
import com.google.firebase.auth.FirebaseAuth

class welcompage : AppCompatActivity() {
    private lateinit var binding: ActivityWelcompageBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityWelcompageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        //bypass login
        if( auth.currentUser != null ) {
            intent = Intent(applicationContext, dashboard::class.java)
            startActivity(intent)
        }

        binding.Signupbuttom.setOnClickListener{
            intent = Intent(applicationContext, signup::class.java)
            startActivity(intent)
        }
        binding.loginbutton.setOnClickListener{
            intent = Intent(applicationContext, login::class.java)
            startActivity(intent)
        }
    }
}