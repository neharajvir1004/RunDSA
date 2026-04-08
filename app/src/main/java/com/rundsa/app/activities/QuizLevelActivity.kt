package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.rundsa.app.R

class QuizLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_level)

        val topicName = intent.getStringExtra("TOPIC") ?: ""

        val titleText = findViewById<TextView>(R.id.quizLevelTitle)
        val easyCard = findViewById<CardView>(R.id.cardEasy)
        val mediumCard = findViewById<CardView>(R.id.cardMedium)
        val hardCard = findViewById<CardView>(R.id.cardHard)
        val backBtn = findViewById<ImageView>(R.id.backBtn)

        titleText.text = "$topicName Quiz Levels"

        easyCard.setOnClickListener {
            openQuizQuestion(topicName, "Easy")
        }

        mediumCard.setOnClickListener {
            openQuizQuestion(topicName, "Medium")
        }

        hardCard.setOnClickListener {
            openQuizQuestion(topicName, "Hard")
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun openQuizQuestion(topic: String, level: String) {
        val intent = Intent(this, QuizQuestionActivity::class.java)
        intent.putExtra("TOPIC", topic)
        intent.putExtra("LEVEL", level)
        startActivity(intent)
    }
}