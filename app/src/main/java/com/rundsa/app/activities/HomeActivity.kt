package com.rundsa.app.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.FeatureAdapter
import com.rundsa.app.models.FeatureModel
import android.content.Intent
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val helloText = findViewById<TextView>(R.id.txtHello)

        // 🔥 UPDATED → reload user to ensure latest data (FIX FOR NAME DELAY)
        val user = FirebaseAuth.getInstance().currentUser
        user?.reload()?.addOnCompleteListener {

            val username = user.displayName ?: "User"
            helloText.text = "Hello, $username!"
        }

        val recycler = findViewById<RecyclerView>(R.id.featureRecycler)

        val featureList = listOf(

            FeatureModel(
                "Learn DSA",
                R.drawable.ic_book,
                R.drawable.card_gradient_dsa
            ),

            FeatureModel(
                "Quizzes",
                R.drawable.ic_quiz,
                R.drawable.card_gradient_quiz
            ),

            FeatureModel(
                "Practice Code",
                R.drawable.ic_code,
                R.drawable.card_gradient_code
            ),

            FeatureModel(
                "Fun Exercise",
                R.drawable.ic_game,
                R.drawable.card_gradient_fun
            ),

            FeatureModel(
                "Progress",
                R.drawable.ic_progress,
                R.drawable.card_gradient_progress
            )
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FeatureAdapter(featureList)

        // Find logout button
        val logoutBtn = findViewById<ImageButton>(R.id.logoutBtn)

        // Click listener
        logoutBtn.setOnClickListener {

            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setCancelable(false)

                .setPositiveButton("Yes") { _, _ ->

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    startActivity(intent)
                    finish()
                }

                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}