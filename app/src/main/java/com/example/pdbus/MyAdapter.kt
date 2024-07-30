package com.example.pdbus

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class MyAdapter(private val userList:ArrayList<user>, private val onItemClick: (Int) -> Unit):
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val databaseRef = FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/").reference



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.users_items,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=userList[position]
//        Log.d("CURRENT:","$currentitem")


        holder.fullname.text=currentitem.fullname
        holder.reg.text=currentitem.regno
        holder.emails.text=currentitem.email
        holder.ph.text=currentitem.phonenum
        holder.Dept.text=currentitem.dept
        holder.yr.text=currentitem.year
        holder.place.text=currentitem.place
        holder.brdplace.text=currentitem.boardplace


        holder.deletebutton.setOnClickListener{
            onItemClick(position)
            val selectedUser = userList[position]
            val selectedUserRef = FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/").
            getReference("Departments/${selectedUser.dept}/${selectedUser.year}/${selectedUser.fullname}")

            selectedUserRef.removeValue().addOnSuccessListener {
                Log.d("name", "User deleted successfully!")
            }.addOnFailureListener {
                Log.d("name", "Error deleting user: ${it.message}")
            }
        }

        holder.acceptButton.setOnClickListener {
            val currentUser = userList[position]
            val newRef = databaseRef.child("Approved").child(currentUser.dept!!).child(currentUser.year!!).
            child(currentUser.fullname!!)
            currentUser.payment = "not paid"
            newRef.setValue(currentUser)
                .addOnSuccessListener {

                    Log.d("MyAdapter", "User ${currentUser.fullname} added to new reference")
                }
                .addOnFailureListener {
                    Log.d("MyAdapter", "Error adding user to new reference: ${it.message}")
                }
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fullname :TextView=itemView.findViewById(R.id.nameid)
        val reg :TextView=itemView.findViewById(R.id.regid)
        val emails :TextView=itemView.findViewById(R.id.emailid)
        val ph :TextView=itemView.findViewById(R.id.phoneid)
        val Dept :TextView=itemView.findViewById(R.id.deptid)
        val yr :TextView=itemView.findViewById(R.id.yrid)
        val place :TextView=itemView.findViewById(R.id.placeid)
        val brdplace :TextView=itemView.findViewById(R.id.brdplaceid)
        val deletebutton:Button=itemView.findViewById(R.id.denyid)
        val acceptButton: Button = itemView.findViewById(R.id.acceptid)
    }
}