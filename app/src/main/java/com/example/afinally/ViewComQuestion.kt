package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.Card
import com.example.afinally.DataClasses.Question
import com.example.afinally.databinding.ActivityPayementmethodBinding
import com.example.afinally.databinding.ActivityViewComQuestionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewComQuestion : AppCompatActivity() {
    private lateinit var binding: ActivityViewComQuestionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private lateinit var quest: Question
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewComQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("questions").child(uid)

        databaseRef.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                quest = snapshot.getValue(Question::class.java)!!
                binding.tvQuest.setText(quest.quest)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewComQuestion, "Failed to fetch Question", Toast.LENGTH_SHORT).show()

            }
        })

        binding.backBtn.setOnClickListener {
            intent = Intent(applicationContext, community::class.java)
            startActivity(intent)

        }

        binding.deleteBtn.setOnClickListener {
            intent = Intent(applicationContext, communitypagetwo::class.java)
            startActivity(intent)
            databaseRef.removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }



    }
}