package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinally.Adapters.SchedulesAdapter
import com.example.afinally.DataClasses.Schedule
import com.example.afinally.databinding.ActivityViewschdulBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class viewschdul : AppCompatActivity() {

    private lateinit var binding: ActivityViewschdulBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Schedule>()
    private lateinit var adapter: SchedulesAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var uid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewschdulBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("schedules").child(uid)

        //configure rv
        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val schedule = snapshot.getValue(Schedule::class.java)!!
                    if( schedule != null){
                        mList.add(schedule)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@viewschdul, error.message, Toast.LENGTH_SHORT).show()
            }

        })
        adapter = SchedulesAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: SchedulesAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, scheduleshow::class.java).also {
                    it.putExtra("landSize", mList[position].landSize)
                    it.putExtra("province", mList[position].province)
                    it.putExtra("landType", mList[position].landType)
                    it.putExtra("plantsAge", mList[position].plantsAge)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }

            }

        })
        binding.btnAdd.setOnClickListener {
            intent = Intent(applicationContext, schedule::class.java)
            startActivity(intent)
        }
    }
}