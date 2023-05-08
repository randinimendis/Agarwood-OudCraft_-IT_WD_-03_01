package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.Card
import com.example.afinally.databinding.ActivityAddcartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class addcart : AppCompatActivity() {
    private lateinit var binding: ActivityAddcartBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddcartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cards").child(uid)

        binding.conBtn.setOnClickListener {
            val name = binding.etName.text.toString()
            val cardNo = binding.etCardNo.text.toString()
            val expYear = binding.etExpYear.text.toString()
            val cvv = binding.etCVV.text.toString()

            //checking if the input fields are empty
            if (name.isEmpty() || cardNo.isEmpty() || expYear.isEmpty() || cvv.isEmpty() ) {
                if (name.isEmpty()) {
                    binding.etName.error = "Enter name"
                }
                if (cardNo.isEmpty()) {
                    binding.etCardNo.error = "Enter card number"
                }
                if (expYear.isEmpty()) {
                    binding.etExpYear.error = "Enter expiration year"
                }
                if (cvv.isEmpty()) {
                    binding.etCVV.error = "Enter CVV"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            } else {
                var id = databaseRef.push().key!!
                val card: Card =
                    Card(name, cardNo, expYear, cvv, id, uid,)
                databaseRef.setValue(card).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, profile::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this,
                            "Something went wrong, try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

    }
}