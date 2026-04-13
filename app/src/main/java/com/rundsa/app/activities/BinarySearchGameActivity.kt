package com.rundsa.app.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class BinarySearchGameActivity : AppCompatActivity() {

    private lateinit var tvQuestionCount: TextView
    private lateinit var tvTarget: TextView
    private lateinit var tvArray: TextView
    private lateinit var tvScore: TextView

    private lateinit var btnIndex0: Button
    private lateinit var btnIndex1: Button
    private lateinit var btnIndex2: Button
    private lateinit var btnIndex3: Button
    private lateinit var btnIndex4: Button
    private lateinit var btnIndex5: Button
    private lateinit var btnIndex6: Button
    private lateinit var btnNext: Button

    private val questionList = listOf(
        Pair(listOf(2, 5, 8, 12, 16, 20, 25), 16),
        Pair(listOf(1, 4, 7, 10, 15, 18, 22), 10),
        Pair(listOf(3, 6, 9, 11, 14, 19, 24), 24),
        Pair(listOf(5, 8, 12, 17, 21, 26, 30), 21),
        Pair(listOf(2, 9, 13, 18, 23, 27, 35), 9)
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var selectedCorrect = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_search_game)

        tvQuestionCount = findViewById(R.id.tvQuestionCount)
        tvTarget = findViewById(R.id.tvTarget)
        tvArray = findViewById(R.id.tvArray)
        tvScore = findViewById(R.id.tvScore)

        btnIndex0 = findViewById(R.id.btnIndex0)
        btnIndex1 = findViewById(R.id.btnIndex1)
        btnIndex2 = findViewById(R.id.btnIndex2)
        btnIndex3 = findViewById(R.id.btnIndex3)
        btnIndex4 = findViewById(R.id.btnIndex4)
        btnIndex5 = findViewById(R.id.btnIndex5)
        btnIndex6 = findViewById(R.id.btnIndex6)
        btnNext = findViewById(R.id.btnNext)

        val buttons = listOf(btnIndex0, btnIndex1, btnIndex2, btnIndex3, btnIndex4, btnIndex5, btnIndex6)

        for (i in buttons.indices) {
            buttons[i].text = "Index $i"

            buttons[i].setOnClickListener {
                animateButton(it)
                checkAnswer(i)
            }
        }

        btnNext.setOnClickListener {
            animateButton(it)
            goToNextQuestion()
        }

        loadQuestion()
    }

    private fun loadQuestion() {
        val (numbers, target) = questionList[currentQuestionIndex]

        tvQuestionCount.text = "Question ${currentQuestionIndex + 1}/5"
        tvArray.text = numbers.joinToString("   ")
        tvTarget.text = "Find target: $target"
        tvScore.text = "Score: $score"
        selectedCorrect = false
    }

    private fun checkAnswer(selectedIndex: Int) {
        val (numbers, target) = questionList[currentQuestionIndex]

        if (selectedCorrect) {
            Toast.makeText(this, "You already answered this question.", Toast.LENGTH_SHORT).show()
            return
        }

        if (numbers[selectedIndex] == target) {
            score++
            selectedCorrect = true
            tvScore.text = "Score: $score"
            Toast.makeText(this, "Correct! Target found at index $selectedIndex", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wrong index. Try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToNextQuestion() {
        if (currentQuestionIndex < questionList.size - 1) {
            currentQuestionIndex++
            loadQuestion()
        } else {
            Toast.makeText(this, "Game finished! Final Score: $score / 5", Toast.LENGTH_LONG).show()
            currentQuestionIndex = 0
            score = 0
            loadQuestion()
        }
    }

    private fun animateButton(view: View) {
        view.animate()
            .scaleX(0.94f)
            .scaleY(0.94f)
            .setDuration(80)
            .withEndAction {
                view.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(80)
                    .start()
            }
            .start()
    }
}