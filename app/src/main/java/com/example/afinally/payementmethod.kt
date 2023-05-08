package com.example.afinally

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.afinally.DataClasses.Card
import com.example.afinally.DataClasses.User
import com.example.afinally.databinding.ActivityPayementmethodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Text

class payementmethod : AppCompatActivity() {
    private lateinit var binding: ActivityPayementmethodBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    private lateinit var databaseRef2: DatabaseReference
    private lateinit var uid: String
    private lateinit var user: User
    private lateinit var card: Card
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayementmethodBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //initialize variables
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        uid = auth.currentUser?.uid.toString()
        databaseRef = FirebaseDatabase.getInstance().reference.child("users")
        databaseRef2 = FirebaseDatabase.getInstance().reference.child("cards").child(uid)
        val totPrice = intent.getStringExtra("totPrice")
        var cardName=""
        var cardNo=""

        databaseRef.child(auth.currentUser!!.uid).addValueEventListener(object :
            ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                user = snapshot.getValue(User::class.java)!!

                binding.tvName.setText(user.name)
                binding.tvEmail.setText(user.email)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@payementmethod, "Failed to fetch user", Toast.LENGTH_SHORT).show()

            }
        })
        databaseRef2.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //retrieve values from the db and convert them to user data class
                card = snapshot.getValue(Card::class.java)!!
                binding.tvCardName.setText(card.name)
                binding.tvCardNo.setText(card.cardNo)
                binding.tvCArdExpYear.setText(card.expYear)
                binding.tvCVV.setText(card.cvv)
                cardName = card.name.toString()
                cardNo = card.cardNo.toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@payementmethod, "Failed to fetch user", Toast.LENGTH_SHORT).show()

            }
        })
        binding.payBtn.setOnClickListener{

            intent = Intent(applicationContext, payementconfirmationsilp::class.java).also {

                it.putExtra("totPrice", totPrice)
                it.putExtra("cardName", cardName)
                it.putExtra("cardNo", cardNo)
//                Toast.makeText(this, totPrice.toString(), Toast.LENGTH_SHORT).show()

                startActivity(it)

            }
        }



    }
}