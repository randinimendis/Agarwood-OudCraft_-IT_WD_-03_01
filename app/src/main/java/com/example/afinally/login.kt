package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth
        auth = FirebaseAuth.getInstance()

        //set onclick listner on login button
        binding.loginBtn.setOnClickListener() {

            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            //checking if the input fields are empty
            if(email.isEmpty() || password.isEmpty()){

                if(email.isEmpty()){
                    binding.etEmail.error = "Enter your email address"
                }
                if(password.isEmpty()){
                    binding.etPassword.error = "Enter your password"
                }
                Toast.makeText(this, "Please enter valid details", Toast.LENGTH_SHORT).show()

            } else if (!email.matches(emailPattern.toRegex())){
                //validate email pattern
                binding.etEmail.error = "Enter a valid email address"
                Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()

            } else if (password.length < 7){
                //validate passwords
                binding.etPassword.error = "Password must be at least 7 characters."
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show()

            } else{
                //Log in
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        intent = Intent(applicationContext, dashboard::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}