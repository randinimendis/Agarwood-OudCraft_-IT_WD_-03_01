package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.User
import com.example.afinally.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var dbRef:DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("cards")
        dbRef=FirebaseDatabase.getInstance().getReference("users")

        dbRef.child(uid).addValueEventListener(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                var user = snapshot.getValue(User::class.java)!!

                binding.tvName.text = user.name
                binding.tvEmail.text = user.email

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@profile, "Faild to fetch user", Toast.LENGTH_SHORT).show()

            }
        })


        binding.addBtn.setOnClickListener {
            //redirect user to login page
            intent = Intent(applicationContext, addcart::class.java)
            startActivity(intent)
        }

        binding.deleteBtn.setOnClickListener {
            databaseRef.child(uid).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                }
            }
        }

      /**/  binding.btnDlt.setOnClickListener {
            var currUser = auth.currentUser
            currUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Account Deleted", Toast.LENGTH_SHORT).show()
                        intent = Intent(applicationContext, welcompage::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Failed to delete the account", Toast.LENGTH_SHORT).show()
                    }
                }
        }



        binding.signOutBtn.setOnClickListener{
            //logout
                Firebase.auth.signOut()
                //redirect user to login page
                intent = Intent(applicationContext, welcompage::class.java)
                startActivity(intent)

                //toast message
            Toast.makeText(this, "Successfully logged out", Toast.LENGTH_SHORT).show()

        }

    }
}