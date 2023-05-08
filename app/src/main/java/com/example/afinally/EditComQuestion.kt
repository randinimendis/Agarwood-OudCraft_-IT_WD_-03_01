package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.databinding.ActivityEditComQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditComQuestion : AppCompatActivity() {

    private lateinit var binding: ActivityEditComQuestionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditComQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("questions").child(uid)

        binding.etAddQus.setText(intent.getStringExtra("quest"))
        val id = intent.getStringExtra("id")

        binding.submitBtn.setOnClickListener {
            var quest = binding.etAddQus.text.toString()
            if(quest.isEmpty()) {
                binding.etAddQus.error = "Enter your question"
            } else {

                val map = HashMap<String,Any>()

                //add data to hashMap
                map["quest"] = quest

                //update database from hashMap
                databaseRef.child(id!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, community::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.deleteBtn.setOnClickListener {
            databaseRef.child(id!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Question deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, community::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}