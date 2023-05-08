package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinally.Adapters.ComQuestAdapter
import com.example.afinally.Adapters.ItemAdapter
import com.example.afinally.DataClasses.ItemData
import com.example.afinally.DataClasses.Question
import com.example.afinally.databinding.ActivityItemeaddBinding
import com.example.afinally.databinding.ActivityViewAllQuestionsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewAllQuestions : AppCompatActivity() {
    private lateinit var binding: ActivityViewAllQuestionsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var mList = ArrayList<Question>()
    private lateinit var adapter: ComQuestAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("questions")

        var recyclerView = binding.recyclerview

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = ComQuestAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: ComQuestAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, ViewComQuestion::class.java).also {
                    it.putExtra("quest", mList[position].quest)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }
            }
        })


    }


    private fun retrieveData() {
        val query = databaseRef.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for (snapshot in snapshot.children) {
                    val quest = snapshot.getValue(Question::class.java)!!
                    if (quest != null) {
                        mList.add(quest)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewAllQuestions, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

}