package com.rundsa.app.activities

import android.os.Bundle
import android.os.Handler
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R
import kotlin.random.Random

class MemoryGameActivity : AppCompatActivity() {

    private lateinit var tvSequence: TextView
    private lateinit var etAnswer: EditText
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    private var sequence = ""
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        tvSequence = findViewById(R.id.tvSequence)
        etAnswer = findViewById(R.id.etAnswer)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)

        generateSequence()

        btnSubmit.setOnClickListener {
            if (etAnswer.text.toString() == sequence) {
                Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong! Correct was $sequence", Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            level++
            generateSequence()
        }
    }

    private fun generateSequence() {
        sequence = ""
        for (i in 1..level + 2) {
            sequence += Random.nextInt(1, 9).toString()
        }

        tvSequence.text = sequence
        etAnswer.setText("")

        // Hide after 2 seconds
        Handler().postDelayed({
            tvSequence.text = "Enter the sequence"
        }, 2000)
    }
}