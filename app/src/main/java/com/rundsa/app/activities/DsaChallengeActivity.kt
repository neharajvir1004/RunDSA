package com.rundsa.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class DsaChallengeActivity : AppCompatActivity() {

    private lateinit var tvQuestion: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button

    private var currentQuestionIndex = 0

    private val questions = listOf(
        Question("Which data structure follows LIFO?", listOf("Queue", "Stack", "Array", "Tree"), 1),
        Question("Which algorithm is used on sorted array?", listOf("Bubble Sort", "DFS", "Binary Search", "Stack"), 2),
        Question("Which traversal uses queue?", listOf("DFS", "BFS", "Recursion", "Sorting"), 1),
        Question("Time complexity of binary search?", listOf("O(n)", "O(log n)", "O(n²)", "O(1)"), 1),
        Question("Which structure stores key-value pairs?", listOf("HashMap", "Stack", "Queue", "Heap"), 0)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dsa_challenge)

        tvQuestion = findViewById(R.id.tvQuestion)
        radioGroup = findViewById(R.id.radioGroup)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        btnSubmit = findViewById(R.id.btnSubmit)
        btnNext = findViewById(R.id.btnNext)

        loadQuestion()

        btnSubmit.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId

            if (selectedId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val selectedIndex = when (selectedId) {
                R.id.option1 -> 0
                R.id.option2 -> 1
                R.id.option3 -> 2
                else -> 3
            }

            if (selectedIndex == questions[currentQuestionIndex].correctIndex) {
                Toast.makeText(this, "Right Answer!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show()
            }
        }

        btnNext.setOnClickListener {
            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                loadQuestion()
            } else {
                Toast.makeText(this, "Challenge Completed!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun loadQuestion() {
        val question = questions[currentQuestionIndex]
        tvQuestion.text = question.question
        option1.text = question.options[0]
        option2.text = question.options[1]
        option3.text = question.options[2]
        option4.text = question.options[3]
        radioGroup.clearCheck()
    }

    data class Question(
        val question: String,
        val options: List<String>,
        val correctIndex: Int
    )
}