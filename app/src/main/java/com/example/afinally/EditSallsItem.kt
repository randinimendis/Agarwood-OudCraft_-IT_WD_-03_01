package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.databinding.ActivityEditSallsItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditSallsItem : AppCompatActivity() {
    private lateinit var binding: ActivityEditSallsItemBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditSallsItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //fetch data from the intent
        val name = intent.getStringExtra("name")
        val dis = intent.getStringExtra("dis")
        val qty = intent.getStringExtra("qty")
        val price = intent.getStringExtra("price")
        val id = intent.getStringExtra("id")

        //bind values to editTexts
        binding.etName.setText(name)
        binding.etPrice.setText(price)
        binding.etQty.setText(qty)
        binding.etDis.setText(dis)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("items")


        binding.editBtn.setOnClickListener {
            var name = binding.etName.text.toString()
            var dis = binding.etDis.text.toString()
            var price = binding.etPrice.text.toString()
            var qty = binding.etQty.text.toString()

            //validate form
            if(name.isEmpty() || dis.isEmpty() || qty.isEmpty()|| price.isEmpty()){

                if(name.isEmpty()){
                    binding.etName.error = "Enter name"
                }
                if(dis.isEmpty()){
                    binding.etDis.error = "Enter description"
                }
                if(qty.isEmpty()){
                    binding.etQty.error = "Enter quantity"
                }
                if(price.isEmpty()){
                    binding.etPrice.error = "Enter quantity"
                }
            } else {
                val map = HashMap<String,Any>()

                //add data to hashMap
                map["name"] = name
                map["dis"] = dis
                map["qty"] = qty
                map["price"] = price



                //update database from hashMap
                databaseRef.child(id!!).updateChildren(map).addOnCompleteListener {
                    if( it.isSuccessful){
                        intent = Intent(applicationContext, sells_iteam::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}