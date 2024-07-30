package com.example.pdbus

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.pdbus.databinding.ActivitySignup1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.UserWriteRecord
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Signup_Activity : AppCompatActivity() {
    private lateinit var binding: ActivitySignup1Binding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    lateinit var butlog: Button
    lateinit var but: Button
    lateinit var terms:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_signup1)
//        but=findViewById(R.id.buttonline)
//        but.setOnClickListener {
//            val database = Firebase.database
//            val myRef = database.getReference("message")
//            myRef.setValue("Hello, World!")
//        }

        binding= ActivitySignup1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        butlog=findViewById(R.id.button3)
        but=findViewById(R.id.buttonline)
        terms=findViewById(R.id.terms)

        val placee=findViewById<Spinner>(R.id.plc)


        binding.plc.adapter=ArrayAdapter.createFromResource(
            this,R.array.place,android.R.layout.simple_spinner_dropdown_item
        )

        binding.plc.onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                when(p0?.getItemAtPosition(p2) as String){
                    "Kovilpatti" -> binding.brdplc.adapter=ArrayAdapter.createFromResource(
                        applicationContext,
                        R.array.kovilpatti_places,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                    "Tuticorin" -> binding.brdplc.adapter = ArrayAdapter.createFromResource(
                        applicationContext,
                        R.array.tuticorin_places,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                    "Tirunelveli" -> binding.brdplc.adapter = ArrayAdapter.createFromResource(
                        applicationContext,
                        R.array.tirunelveli_places,
                        android.R.layout.simple_spinner_dropdown_item
                    )
                }
            }
            override fun onNothingSelected(po: AdapterView<*>) {
                // Do nothing
            }
        }


        auth = FirebaseAuth.getInstance()
//        auth.applyActionCode()
        but.setOnClickListener{

            val fullname=binding.name1.text.toString().trim()
            if (fullname.isEmpty()) {
                binding.name1.error = "Name cannot be empty"
            } else if (!fullname.matches(Regex("[a-zA-Z\\s]+"))) {
                binding.name1.error = "Name must contain only letters and spaces"
            } else {
                binding.name1.error = null
            }

            val regno=binding.reg1.text.toString().trim()
            if (regno.isEmpty()) {
                binding.reg1.error = "Register Number cannot be empty"
            } else if (!regno.matches(Regex("^[0-9]{7}$"))) {
                binding.reg1.error = "Enter Your Register Number Correctly"
            } else {
                binding.reg1.error = null
            }

            val email = binding.email1.text.toString().trim()
            if (email.isEmpty()) {
                binding.email1.error = "Email cannot be empty"
            } else if (!email.matches(Regex("^[0-9]{7}@nec\\.edu\\.in$"))) {
                binding.email1.error = "Enter Your College Email Id"
            } else {
                binding.email1.error = null
            }

            val password = binding.pass1.text.toString().trim()
            if (password.isEmpty()) {
                binding.pass1.error = "Password cannot be empty"
            } else if (!password.matches(Regex("^(?=.*[A-Za-z]{4,})(?=.*\\d{3,})(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}\$"))) {
                binding.pass1.error = "Your Password must be minimum of 8 charcters with 1 special character and 3 numbers"
            } else {
                binding.pass1.error = null
            }

            val phonenum = binding.phnum.text.toString().trim()
            if (phonenum.isEmpty()) {
                binding.phnum.error = "Phone Number cannot be empty"
            } else if (!phonenum.matches(Regex("^[0-9]{10}$"))) {
                binding.phnum.error = "Enter Phone Number Correctly"
            } else {
                binding.phnum.error = null
            }

            val dept=binding.dept.selectedItem.toString()
            val year=binding.year.selectedItem.toString()
            val place=binding.plc.selectedItem.toString()
            val boardplace=binding.brdplc.selectedItem.toString()


            if(binding.name1.error==null && binding.reg1.error==null && binding.email1.error==null && binding.pass1.error==null &&
                binding.phnum.error==null) {
                if(binding.check.isChecked){
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Send email verification
                            val user = auth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { emailTask ->
                                    if (emailTask.isSuccessful) {
                                        Log.d(TAG, "Email sent.")
                                        database = FirebaseDatabase.getInstance("https://transport-9e223-default-rtdb.asia-southeast1.firebasedatabase.app/")
                                                .getReference("Departments")
                                        val user = user(fullname, regno, email, password, phonenum, dept, year, place, boardplace)
                                        database.child(dept).child(year).child(fullname).setValue(user).addOnSuccessListener {
                                            binding.name1.text.clear()
                                            binding.reg1.text.clear()
                                            binding.email1.text.clear()
                                            binding.pass1.text.clear()
                                            binding.phnum.text.clear()

                                            verifydialog()
                                            Toast.makeText(this, "Verification Email Has Been Sent To Your Email",
                                                Toast.LENGTH_LONG).show()
                                        }
                                    } else {
                                        Log.e(TAG, "Error sending email verification.", emailTask.exception)
                                    }
                                }
                        } else {
                            Toast.makeText(this, "Email Already Exists", Toast.LENGTH_LONG).show()
                        }
                    }
            }
                else{
                    Toast.makeText(this, "Agree To Terms And Conditions", Toast.LENGTH_LONG).show()
                }
            }
        }


        butlog.setOnClickListener{
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
        }
        terms.setOnClickListener{
            val intent = Intent(this,terms_activity::class.java)
            startActivity(intent)
        }

    }
    private fun verifydialog() {

//        val verifylayout = findViewById<ConstraintLayout>(R.id.dialogbox)
        val view = LayoutInflater.from(this).inflate(R.layout.activity_dialog, null)
        val log = view.findViewById<Button>(R.id.verlog)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        val alertDialog = builder.create()

        log.findViewById<Button>(R.id.verlog).setOnClickListener {
            alertDialog.dismiss()
            val intent = Intent(this,Login_Activity::class.java)
            startActivity(intent)
        }
        if(alertDialog.window!=null){
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }
    companion object {
        private const val TAG = "Signup_Activity"
    }
}