package com.example.afinally

import android.content.ClipData.Item
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinally.Adapters.ItemAdapter
import com.example.afinally.DataClasses.ItemData
import com.example.afinally.databinding.ActivitySellsIteamBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class sells_iteam : AppCompatActivity() {
    private lateinit var binding: ActivitySellsIteamBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var uid: String
    private var mList = ArrayList<ItemData>()
    private lateinit var adapter: ItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySellsIteamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize variables
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("items")


        var recyclerView = binding.recyclerview

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this);

        //addDataToList()
        retrieveData()
        adapter = ItemAdapter(mList)
        recyclerView.adapter = adapter

        //Setting onclick on recyclerView each item
        adapter.setOnItemClickListner(object: ItemAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, sells::class.java).also {
                    it.putExtra("name", mList[position].name)
                    it.putExtra("qty", mList[position].qty)
                    it.putExtra("price", mList[position].price)
                    it.putExtra("dis", mList[position].dis)
                    it.putExtra("id", mList[position].id)
                    startActivity(it)
                }
            }
        })




        binding.addBtn.setOnClickListener {
            intent = Intent(applicationContext, itemeadd::class.java)
            startActivity(intent)
        }

    }

    private fun retrieveData() {
        val query = databaseRef.orderByChild("uid").equalTo(uid)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(ItemData::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@sells_iteam, error.message, Toast.LENGTH_SHORT).show()
            }

        })
    }
}