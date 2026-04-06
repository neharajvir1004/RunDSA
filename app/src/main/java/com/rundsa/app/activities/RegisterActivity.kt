package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.rundsa.app.R
import com.google.firebase.auth.UserProfileChangeRequest // for display username

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        // ADDED (name field)
        val name = findViewById<EditText>(R.id.nameEditText)

        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val loginText = findViewById<TextView>(R.id.loginText)

        registerBtn.setOnClickListener {

            // ADDED (get name)
            val userName = name.text.toString().trim()

            val userEmail = email.text.toString().trim()
            val userPassword = password.text.toString().trim()

            // 🔥 UPDATED (include name check)
            if (userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // FIREBASE REGISTER
            auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        val user = auth.currentUser

                        // 🔥 SAVE NAME TO FIREBASE
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(userName)
                            .build()

                        // 🔥 IMPORTANT FIX → wait for update + reload before moving
                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener {

                                // 🔥 Force refresh user data (MAIN FIX)
                                user.reload().addOnCompleteListener {

                                    Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()

                                    // 🔥 Now name will be available instantly
                                    val intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)

                                    finish()
                                }
                            }

                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        }

        // GO BACK TO LOGIN
        loginText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}