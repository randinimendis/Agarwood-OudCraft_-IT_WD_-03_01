package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.ItemData
import com.example.afinally.databinding.ActivitySellsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class sells : AppCompatActivity() {
    private lateinit var binding: ActivitySellsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("items")

        //fetch data from the intent
        val name = intent.getStringExtra("name")
        val qty = intent.getStringExtra("qty")
        val price = intent.getStringExtra("price")
        val dis = intent.getStringExtra("dis")
        val id = intent.getStringExtra("id")

        //bind values to editTexts
        binding.tvName.text=name
        binding.tvQty.text=qty
        binding.tvPrice.text=price
        binding.tvDis.text=dis

        binding.updateBtn.setOnClickListener {
            intent = Intent(applicationContext, EditSallsItem::class.java).also {

                    it.putExtra("name", name)
                    it.putExtra("qty", qty)
                    it.putExtra("price", price)
                    it.putExtra("dis", dis)
                    it.putExtra("id", id)
                    startActivity(it)

            }
        }
//delete
        binding.deleteBtn.setOnClickListener {
            databaseRef.child(id!!).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, sells_iteam::class.java)
                    startActivity(intent)
                }
            }
        }


    }
}