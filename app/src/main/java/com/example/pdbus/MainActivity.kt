package com.example.pdbus

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    lateinit var butlog: Button
    lateinit var butsign: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        butlog=findViewById(R.id.button)
        butlog.setOnClickListener{
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
        }
        butsign=findViewById(R.id.button2)
        butsign.setOnClickListener{
            val intent = Intent(this,Signup_Activity::class.java)
            startActivity(intent)
        }
    }
}
