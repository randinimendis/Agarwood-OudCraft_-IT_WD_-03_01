package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.databinding.ActivityUpdateScheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateSchedule : AppCompatActivity() {


    private lateinit var binding: ActivityUpdateScheduleBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("schedules").child(uid)

        var landSize = intent.getStringExtra("landSize")
        var province = intent.getStringExtra("province")
        var landType = intent.getStringExtra("landType")
        var plantsAge = intent.getStringExtra("plantsAge")
        var id = intent.getStringExtra("id").toString()


        binding.etLandSize.setText(landSize)


        binding.btnUpdate.setOnClickListener {
             landSize = binding.etLandSize.text.toString()
             province = binding.spinnerProvince.selectedItem.toString()
             landType = binding.spinnerLandType.selectedItem.toString()
             plantsAge = binding.spinnerPlantsAge.selectedItem.toString()

            val map = HashMap<String,Any>()

            //add data to hashMap
            map["landSize"] = landSize!!
            map["province"] = province!!
            map["landType"] = landType!!
            map["plantsAge"] = plantsAge!!


            //update database from hashMap
            databaseRef.child(id).updateChildren(map).addOnCompleteListener {
                if( it.isSuccessful){
                    intent = Intent(applicationContext, viewschdul::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Schedule updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}