package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.Schedule
import com.example.afinally.databinding.ActivityScheduleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class schedule : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("schedules").child(uid)

        binding.btnSubmit.setOnClickListener {
            var landSize = binding.etLandSize.text.toString()
            var province = binding.spinnerProvince.selectedItem.toString()
            var landType = binding.spinnerLandType.selectedItem.toString()
            var plantsAge = binding.spinnerPlantsAge.selectedItem.toString()

            if( landSize.isEmpty() ) {
                binding.etLandSize.error = "Please enter land size"
            } else {
                var id = databaseRef.push().key!!

                val schedule = Schedule( landSize,province, landType, plantsAge, id)
                databaseRef.child(id).setValue(schedule).addOnCompleteListener {
                    if (it.isSuccessful){
                        intent = Intent(applicationContext, viewschdul::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Schedule submitted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding.btnClear.setOnClickListener {
            binding.etLandSize.setText("")
        }



    }
}