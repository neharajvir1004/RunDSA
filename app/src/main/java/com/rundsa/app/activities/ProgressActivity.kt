package com.rundsa.app.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rundsa.app.R

class ProgressActivity : AppCompatActivity() {

    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        resultText = findViewById(R.id.resultText)

        loadProgress()
    }

    // 🔥 Fetch data from Firebase
    private fun loadProgress() {

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            db.collection("quiz_results")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { documents ->

                    if (documents.isEmpty) {
                        resultText.text = "No progress yet"
                        return@addOnSuccessListener
                    }

                    val resultBuilder = StringBuilder()

                    for (doc in documents) {
                        val topic = doc.getString("topic") ?: "Unknown"
                        val level = doc.getString("level") ?: ""
                        val score = doc.getLong("score") ?: 0
                        //val total = doc.getLong("totalQuestions") ?: 0
                        val total = 10   // 🔥 FORCE FIX

                        resultBuilder.append("📘 $topic ($level)\n")
                        resultBuilder.append("Score: $score / $total\n\n")
                    }

                    resultText.text = resultBuilder.toString()
                }
                .addOnFailureListener {
                    resultText.text = "Error loading data"
                }

        } else {
            resultText.text = "Please login first"
        }
    }
}