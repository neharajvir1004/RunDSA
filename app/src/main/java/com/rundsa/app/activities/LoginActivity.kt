package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val guestBtn = findViewById<Button>(R.id.guestBtn)

        // LOGIN BUTTON
        loginBtn.setOnClickListener {

            val username = emailEditText.text.toString()

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", username)

            startActivity(intent)
        }

        // GUEST BUTTON
        guestBtn.setOnClickListener {

            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USERNAME", "User")

            startActivity(intent)
        }
    }
}