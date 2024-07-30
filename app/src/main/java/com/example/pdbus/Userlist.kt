package com.example.pdbus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class Userlist : AppCompatActivity() {
    private lateinit var dbref:DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<user>
    private lateinit var array:Array<String>
    private lateinit var array1:Array<String>

    private lateinit var auth: FirebaseAuth

    private lateinit var spinnerDept: Spinner
    private lateinit var spinnerYear: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userlist)


        userRecyclerView=findViewById(R.id.userslist)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        array= arrayOf("Computer Science And Engineering","Information Technology","Electrical And Electronics Engineering",
            "Electrical And Communication Engineering","Mechanical Engineering","Civil Engineering")
        array1= arrayOf("1","2","3","4")
        userArrayList= arrayListOf<user>()


        spinnerDept = findViewById(R.id.deptrec)
        spinnerYear = findViewById(R.id.yearrec)
        val adapterDept = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array)
        spinnerDept.adapter = adapterDept
        val adapterYear = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array1)
        spinnerYear.adapter = adapterYear


        auth = FirebaseAuth.getInstance()

        spinnerDept.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDept = parent.getItemAtPosition(position) as String
                val selectedYear = spinnerYear.selectedItem as String
                getUserData1(selectedDept, selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedYear = parent.getItemAtPosition(position) as String
                val selectedDept = spinnerDept.selectedItem as String
                getUserData1(selectedDept, selectedYear)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        for(i in array){
            for(j in array1){
                getUserData(i,j)
            }
        }
    }

    private fun getUserData(i:String,j:String){
        dbref=FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/").
        getReference("Departments/$i/$j")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user =userSnapshot.getValue(user::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter=MyAdapter(userArrayList){position ->
                        val selectedUser =userArrayList[position]
                        val selectedUserRef = dbref.child(selectedUser.fullname!!)

                        auth.signInWithEmailAndPassword(selectedUser.email!!, selectedUser.password!!)
                            .addOnSuccessListener {
                                val user = auth.currentUser
                                user!!.delete()
                                    .addOnSuccessListener {
                                        Log.d("auth", "User authentication deleted successfully")
                                    }.addOnFailureListener {
                                        Log.d("auth", "Error deleting user authentication: ${it.message}")
                                    }
                            }.addOnFailureListener {
                                Log.d("auth", "Error signing in user: ${it.message}")
                            }

                        selectedUserRef.removeValue().addOnSuccessListener {
                            userArrayList.removeAt(position)
                            userRecyclerView.adapter?.notifyItemRemoved(position)
                        }.addOnFailureListener {
                            Log.d("name", "Error deleting user: ${it.message}")
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        )
    }



    private fun getUserData1(selectedDept: String, selectedYear: String) {
        dbref = FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .getReference("Departments/$selectedDept/$selectedYear")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(user::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter?.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }


}