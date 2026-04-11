package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

// 🔥 Firebase imports
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

// 🔥 Model import
import com.rundsa.app.models.QuizResult

class QuizResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_result)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 10)

        val scoreText = findViewById<TextView>(R.id.scoreText)
        val badgeText = findViewById<TextView>(R.id.badgeText)
        val messageText = findViewById<TextView>(R.id.messageText)
        val retryBtn = findViewById<Button>(R.id.retryBtn)
        val homeBtn = findViewById<Button>(R.id.homeBtn)

        // ✅ Show score
        scoreText.text = "$score / $total"

        // ✅ Result logic
        if (score >= total / 2) {
            badgeText.text = "PASS"
            messageText.text = "Great job! You did well."
        } else {
            badgeText.text = "FAIL"
            messageText.text = "Keep practicing and try again."
        }

        // ✅ Animation
        val anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        scoreText.startAnimation(anim)
        badgeText.startAnimation(anim)

        val topic = intent.getStringExtra("TOPIC") ?: "Arrays"
        val level = intent.getStringExtra("LEVEL") ?: "Easy"

        // 🔥 SAVE TO FIREBASE (ONLY ONCE)
        if (savedInstanceState == null) {
            saveResultToFirebase(topic, level, score, total)
        }

        // 🔁 Retry button
        retryBtn.setOnClickListener {
            val intent = Intent(this, QuizQuestionActivity::class.java)
            intent.putExtra("TOPIC", topic)
            intent.putExtra("LEVEL", level)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // 🏠 Home button
        homeBtn.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    // 🔥 FIREBASE SAVE FUNCTION (UPDATED WITH MODEL + EMAIL)
    private fun saveResultToFirebase(topic: String, level: String, score: Int, total: Int) {

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        val result = QuizResult(
            userId = user?.uid ?: "guest",
            email = user?.email ?: "guest",
            topic = topic,
            level = level,
            score = score,
            totalQuestions = total,
            timestamp = System.currentTimeMillis()
        )

        db.collection("quiz_results")
            .add(result)
            .addOnSuccessListener {
                // ✅ success (optional log/toast)
            }
            .addOnFailureListener {
                // ❌ error (optional log)
            }
    }
}