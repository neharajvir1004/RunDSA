package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.rundsa.app.R

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val guestBtn = findViewById<Button>(R.id.guestBtn)
        val registerText = findViewById<TextView>(R.id.registerText)

        // 🔥 ADDED (Forgot Password TextView)
        val forgotPasswordText = findViewById<TextView>(R.id.forgotPasswordText)

        // =========================
        // LOGIN BUTTON FUNCTION
        // =========================
        loginBtn.setOnClickListener {

            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, HomeActivity::class.java)
                        intent.putExtra("USERNAME", email)
                        startActivity(intent)

                        finish()

                    } else {

                        val exception = task.exception

                        when (exception) {

                            is FirebaseAuthInvalidUserException -> {
                                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                            }

                            is FirebaseAuthInvalidCredentialsException -> {

                                val errorCode = exception.errorCode

                                if (errorCode == "ERROR_USER_NOT_FOUND") {
                                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()

                                } else if (errorCode == "ERROR_WRONG_PASSWORD") {
                                    Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()

                                } else {
                                    Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show()
                                }
                            }

                            else -> {
                                Toast.makeText(this, "Login failed. Try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
        }

        // =========================
        //FORGOT PASSWORD FUNCTION
        // =========================
        forgotPasswordText.setOnClickListener {

            val email = emailEditText.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // =========================
        // GUEST BUTTON FUNCTION
        // =========================
        guestBtn.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", "Guest")
            startActivity(intent)
        }

        // =========================
        // REGISTER TEXT CLICK
        // =========================
        registerText.setOnClickListener {

            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // =========================
    // AUTO LOGIN FUNCTION
    // =========================
    override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", currentUser.email)

            startActivity(intent)
            finish()
        }
    }
}