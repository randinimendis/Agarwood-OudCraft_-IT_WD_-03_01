package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.ItemData
import com.example.afinally.databinding.ActivityItemeaddBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class itemeadd : AppCompatActivity() {
    private lateinit var binding: ActivityItemeaddBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemeaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("items")

        binding.addBtn.setOnClickListener {
            val name = binding.etName.text.toString()
            val qty = binding.etQty.text.toString()
            val price = binding.etPrice.text.toString()
            val dis = binding.etDis.text.toString()

            //checking if the input fields are empty
            if (name.isEmpty() || qty.isEmpty() || price.isEmpty() || dis.isEmpty()) {
                if (name.isEmpty()) {
                    binding.etName.error = "Enter name"
                }
                if (qty.isEmpty()) {
                    binding.etQty.error = "Enter quantity"
                }
                if (price.isEmpty()) {
                    binding.etPrice.error = "Enter price"
                }
                if (dis.isEmpty()) {
                    binding.etDis.error = "Enter description"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }else {
                //create
                var id = databaseRef.push().key!!
                val item: ItemData =
                    ItemData(name, qty, price, dis, id, uid,)
                databaseRef.child(id).setValue(item).addOnCompleteListener {
                    if (it.isSuccessful) {
                        //redirect user to login activity
                        val intent = Intent(this, sells_iteam::class.java)
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