package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.Question
import com.example.afinally.databinding.ActivityAddcartBinding
import com.example.afinally.databinding.ActivityCommunitypagetwoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class communitypagetwo : AppCompatActivity() {
    private lateinit var binding: ActivityCommunitypagetwoBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunitypagetwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("questions").child(uid)

        binding.submitBtn.setOnClickListener {
            val quest = binding.etAddQus.text.toString()


            //checking if the input fields are empty
            if (quest.isEmpty()) {
                if (quest.isEmpty()) {
                    binding.etAddQus.error = "Enter question"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            } else {
                var id = databaseRef.push().key!!
                val quest: Question =
                    Question(quest, id, uid,)
                databaseRef.child(id).setValue(quest).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, community::class.java)
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