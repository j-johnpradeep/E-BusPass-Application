package com.example.pdbus

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class MyAdapter1(private val userList:ArrayList<user>):
    RecyclerView.Adapter<MyAdapter1.MyViewHolder>() {

    private val databaseRef = FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.approved,parent,false)
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
    }
}