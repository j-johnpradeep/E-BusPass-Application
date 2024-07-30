package com.example.pdbus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class intro_activity : AppCompatActivity() {
    lateinit var butlog: Button
    lateinit var butstudent: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        butlog=findViewById(R.id.button)
        butlog.setOnClickListener{

            val intent = Intent(this,Login_Activity::class.java)
            intent.putExtra("name","Welcome Admin,login here");
            intent.putExtra("email","Enter Your Email Here");
            startActivity(intent)
        }
        butstudent=findViewById(R.id.button2)
        butstudent.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}