package com.example.pdbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class approvedlistActivity : AppCompatActivity() {
    private lateinit var dbref:DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<user>
    private lateinit var array:Array<String>
    private lateinit var array1:Array<String>

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approvedlist)


        userRecyclerView=findViewById(R.id.approvedlist)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        array= arrayOf("Computer Science And Engineering","Information Technology","Electrical And Electronics Engineering",
            "Electrical And Communication Engineering","Mechanical Engineering","Civil Engineering")
        array1= arrayOf("1","2","3","4")
        userArrayList= arrayListOf<user>()

        auth = FirebaseAuth.getInstance()

        for(i in array){
            for(j in array1){
                getUserData(i,j)
            }
        }
    }
    private fun getUserData(i:String,j:String){
        dbref=FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/").
        getReference("Approved/$i/$j")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user =userSnapshot.getValue(user::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter=MyAdapter1(userArrayList)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )
    }
}