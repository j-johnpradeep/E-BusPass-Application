package com.example.pdbus

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var signupbutton:Button
    private lateinit var adm: TextView
    private lateinit var admemail:EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login1)

        val bundle=intent.extras;
        var admin: String= ""
        var emailadmin:String=""

        auth = FirebaseAuth.getInstance()
        emailEditText = findViewById(R.id.emaillog)
        passwordEditText = findViewById(R.id.passlog)
        loginButton = findViewById(R.id.button35)
        signupbutton=findViewById(R.id.button3)
        adm =findViewById(R.id.textadmin)
        admemail=findViewById(R.id.emaillog)
        val parent=findViewById<ViewGroup>(R.id.loginlayout)



        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            if (email.isEmpty()) {
                emailEditText.error = "Email cannot be empty"
            } else if (!email.matches(Regex("^[0-9]{7}@nec\\.edu\\.in$"))) {
                emailEditText.error = "Enter Your College Email Id"
            } else {
                emailEditText.error = null
            }
            val password = passwordEditText.text.toString()
            if (password.isEmpty()) {
                passwordEditText.error = "Password cannot be empty"
            } else if (!password.matches(Regex("^(?=.*[A-Za-z]{4,})(?=.*\\d{3,})(?=.*[@\$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}\$"))) {
                passwordEditText.error =
                    "Your Password must be minimum of 8 charcters with 1 special character and 3 numbers"
            } else {
                passwordEditText.error = null
            }

            // Call the signInWithEmailAndPassword method on the auth instance with the email and password arguments
            if (emailEditText.error == null && passwordEditText.error == null){
                if(email.equals("2015043@nec.edu.in")&&password.equals("Sundar123#")){
                    val intent = Intent(this,AdminActivity::class.java)
                    startActivity(intent)
                }

                else {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Check if the user's email has been verified
                                val user = auth.currentUser;
//                            Toast.makeText(this, "user"+user,Toast.LENGTH_SHORT).show()
                                if (user != null && user.isEmailVerified) {
                                    // User is signed in and email is verified
                                    // Perform any necessary actions, such as navigating to the next screen
                                    val intent = Intent(this, DashboardActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    // User is signed in but email is not verified
                                    // Display a message to the user prompting them to check their email and verify their account
                                    Toast.makeText(this, "Please verify your email address.", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                // Handle sign-in errors
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_LONG).show()
                            }
                        }
                }
                }
        }
        if(bundle!=null) {
            admin = bundle.getString("name").toString()
            adm.setText(admin)
            emailadmin=bundle.getString("email").toString()
            admemail.setHint(emailadmin)
//            signupbutton.visibility= View.INVISIBLE
            parent.visibility=View.INVISIBLE

            loginButton.setOnClickListener {
                val email = emailEditText.text.toString()
                if (email.isEmpty()) {
                    emailEditText.error = "Email cannot be empty"
                }
                val password = passwordEditText.text.toString()
                if (password.isEmpty()) {
                    passwordEditText.error = "Password cannot be empty"
                }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Check if the user's email has been verified
                            val user = auth.currentUser;
//                            Toast.makeText(this, "user"+user,Toast.LENGTH_SHORT).show()
                            if (user != null) {
                                // User is signed in and email is verified
                                // Perform any necessary actions, such as navigating to the next screen
                                val intent = Intent(this, AdminActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
            }
        }
            signupbutton.setOnClickListener{
                val intent = Intent(this,Signup_Activity::class.java)
                startActivity(intent)
        }
    }
}
