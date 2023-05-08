package com.example.afinally

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinally.databinding.ActivityIteamveiewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class iteamveiew : AppCompatActivity() {
    private lateinit var binding: ActivityIteamveiewBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var count = 0
    private var totPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIteamveiewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
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
        binding.tvPrice.text=price
        binding.tvDis.text=dis

        binding.incrementButton.setOnClickListener {
            count += 10
            binding.countTextView.text = count.toString()
            val priceInt=price!!.toInt()
            totPrice=priceInt*(count/10)
            binding.tvTotPrice.text=totPrice.toString()
        }
        binding.decrementButton.setOnClickListener {
            if (count!=0) {
                count -= 10
                binding.countTextView.text = count.toString()
                val priceInt=price!!.toInt()
                totPrice=priceInt*(count/10)
                binding.tvTotPrice.text=totPrice.toString()
            }
        }
        binding.buyBtn.setOnClickListener{

            intent = Intent(applicationContext, payementmethod::class.java).also {
                val priceInt=price!!.toInt()
                totPrice=priceInt*(count/10)
                it.putExtra("totPrice", totPrice.toString())
//                Toast.makeText(this, totPrice.toString(), Toast.LENGTH_SHORT).show()
                startActivity(it)

            }
        }

//        binding.buyBtn.setOnClickListener {
//            val priceInt = price!!.toInt()
//            totPrice = priceInt * (count / 10)
//            val intent = Intent(this, payementmethod::class.java)
//            intent.putExtra("totPrice", totPrice)
//            startActivity(intent)
//        }




    }
}