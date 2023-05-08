package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.databinding.ActivityScheduleshowBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class scheduleshow : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleshowBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleshowBinding.inflate(layoutInflater)
        setContentView(binding.root)




        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("schedules").child(uid)

        val landSize = intent.getStringExtra("landSize")
        val province = intent.getStringExtra("province")
        val landType = intent.getStringExtra("landType")
        val plantsAge = intent.getStringExtra("plantsAge")
        val id = intent.getStringExtra("id").toString()


        var totPlants = landSize!!.toInt() * 4
        binding.tvTot.text = totPlants.toString()

        when(landType) {
            "Hard ground" -> binding.tvLand.text = getString(R.string.hardGround)
            "Not" -> binding.tvLand.text = getString(R.string.notHardGround)
        }


        binding.btnUpdate.setOnClickListener {
            intent = Intent(applicationContext, UpdateSchedule::class.java).also {
                it.putExtra("landSize", landSize)
                it.putExtra("province",  province)
                it.putExtra("landType", landType)
                it.putExtra("plantsAge", plantsAge)
                it.putExtra("id", id)
                startActivity(it)
            }
        }


        binding.btnDlt.setOnClickListener {
            databaseRef.child(id).removeValue().addOnCompleteListener {
                if( it.isSuccessful){
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, viewschdul::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}