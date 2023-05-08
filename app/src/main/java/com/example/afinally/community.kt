package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinally.Adapters.ComQuestAdapter
import com.example.afinally.DataClasses.Question
import com.example.afinally.databinding.ActivityAddcartBinding
import com.example.afinally.databinding.ActivityCommunityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class community : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Question>()
    private lateinit var adapter: ComQuestAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("questions").child(uid)

        //configure rv
        recyclerView = binding.comQuestRv
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(Question::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@community, error.message, Toast.LENGTH_SHORT).show()
            }

        })
        adapter = ComQuestAdapter(mList)
        recyclerView.adapter = adapter


        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: ComQuestAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, EditComQuestion::class.java).also {
                    it.putExtra("quest", mList[position].quest)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }
            }
        })

        binding.addQusBtn.setOnClickListener {
            intent = Intent(applicationContext, communitypagetwo::class.java)
            startActivity(intent)

        }
    }
}