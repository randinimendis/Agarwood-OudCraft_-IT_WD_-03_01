package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.User
import com.example.afinally.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.log

class signup : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing auth and database variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.signUpBtn.setOnClickListener {
            val name = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPass.text.toString()
            val confirmPwd = binding.etConPass.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPwd.isEmpty()) {
                if (name.isEmpty()) {
                    binding.etName.error = "Enter your name"
                }
                if (email.isEmpty()) {
                    binding.etEmail.error = "Enter your email"
                }

                if (password.isEmpty()) {
                    binding.etPass.error = "Enter your password"
                }
                if (confirmPwd.isEmpty()) {
                    binding.etConPass.error = "Re-enter your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }
            //validate email pattern
            else if (!email.matches(emailPattern.toRegex())) {
                binding.etEmail.error = "Enter a valid email address"
            }

            //validate passwords
            else if (password.length < 7) {
                binding.etPass.error = "Password must be at least 7 characters."
            } else if (confirmPwd != password) {
                binding.etConPass.error = "Passwords do not match. Please try again."
            } else {
//                Toast.makeText(this, "All good", Toast.LENGTH_SHORT).show()
                //creating an auth for the user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {

                    if (it.isSuccessful) {
                        //store user details in the database
                        val databaseRef =
                            database.reference.child("users").child(auth.currentUser!!.uid)
                        val user: User = User(name, email, auth.currentUser!!.uid)
                        databaseRef.setValue(user).addOnCompleteListener {
                            if (it.isSuccessful) {
                                //redirect user to login activity
                                val intent = Intent(this, login::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Something went wrong, try again",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    } else {
                        Toast.makeText(this, "Something went wrong, try again", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }


        }
    }
}