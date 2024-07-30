package com.example.pdbus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        auth = FirebaseAuth.getInstance()

        val logout=findViewById<LinearLayout>(R.id.alogout)
        val llayout=findViewById<LinearLayout>(R.id.approvalpend)
        val alayout=findViewById<LinearLayout>(R.id.approvelist)
        logout.setOnClickListener{
            val user = auth.currentUser;
            if(user!=null){
                auth.signOut()
                val intent = Intent(this, Login_Activity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.putExtra("name","Welcome Admin,login here");
                intent.putExtra("email","Enter Your Email Here");
                startActivity(intent)
                finish()
            }
        }
        llayout.setOnClickListener{
            val intent =Intent(this,Userlist::class.java)
            startActivity(intent)
        }
        alayout.setOnClickListener{
            val intent =Intent(this,approvedlistActivity::class.java)
            startActivity(intent)
        }
    }
}