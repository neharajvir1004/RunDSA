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
import kotlin.random.Random

class SortingGameActivity : AppCompatActivity() {

    private lateinit var tvGoal: TextView
    private lateinit var tvMoves: TextView
    private lateinit var tvScore: TextView

    private lateinit var btnSwap1: Button
    private lateinit var btnSwap2: Button
    private lateinit var btnSwap3: Button
    private lateinit var btnSwap4: Button
    private lateinit var btnSwap5: Button
    private lateinit var btnSwap6: Button
    private lateinit var btnSwap7: Button
    private lateinit var btnSwap8: Button
    private lateinit var btnSwap9: Button
    private lateinit var btnSwap10: Button
    private lateinit var btnSwap11: Button
    private lateinit var btnSwap12: Button
    private lateinit var btnSwap13: Button
    private lateinit var btnSwap14: Button
    private lateinit var btnCheck: Button
    private lateinit var btnReset: Button

    private lateinit var numberViews: List<TextView>

    private var numbers = mutableListOf<Int>()
    private var moves = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorting_game)

        tvGoal = findViewById(R.id.tvGoal)
        tvMoves = findViewById(R.id.tvMoves)
        tvScore = findViewById(R.id.tvScore)

        btnSwap1 = findViewById(R.id.btnSwap1)
        btnSwap2 = findViewById(R.id.btnSwap2)
        btnSwap3 = findViewById(R.id.btnSwap3)
        btnSwap4 = findViewById(R.id.btnSwap4)
        btnSwap5 = findViewById(R.id.btnSwap5)
        btnSwap6 = findViewById(R.id.btnSwap6)
        btnSwap7 = findViewById(R.id.btnSwap7)
        btnSwap8 = findViewById(R.id.btnSwap8)
        btnSwap9 = findViewById(R.id.btnSwap9)
        btnSwap10 = findViewById(R.id.btnSwap10)
        btnSwap11 = findViewById(R.id.btnSwap11)
        btnSwap12 = findViewById(R.id.btnSwap12)
        btnSwap13 = findViewById(R.id.btnSwap13)
        btnSwap14 = findViewById(R.id.btnSwap14)
        btnCheck = findViewById(R.id.btnCheck)
        btnReset = findViewById(R.id.btnReset)

        numberViews = listOf(
            findViewById(R.id.num0),
            findViewById(R.id.num1),
            findViewById(R.id.num2),
            findViewById(R.id.num3),
            findViewById(R.id.num4),
            findViewById(R.id.num5),
            findViewById(R.id.num6),
            findViewById(R.id.num7),
            findViewById(R.id.num8),
            findViewById(R.id.num9),
            findViewById(R.id.num10),
            findViewById(R.id.num11),
            findViewById(R.id.num12),
            findViewById(R.id.num13),
            findViewById(R.id.num14)
        )

        tvGoal.text = "Sort the 15 numbers in ascending order"

        generateNumbers()
        updateNumbers()
        updateMoves()
        updateScore()

        btnSwap1.setOnClickListener { animateButton(it); swap(0, 1) }
        btnSwap2.setOnClickListener { animateButton(it); swap(1, 2) }
        btnSwap3.setOnClickListener { animateButton(it); swap(2, 3) }
        btnSwap4.setOnClickListener { animateButton(it); swap(3, 4) }
        btnSwap5.setOnClickListener { animateButton(it); swap(4, 5) }
        btnSwap6.setOnClickListener { animateButton(it); swap(5, 6) }
        btnSwap7.setOnClickListener { animateButton(it); swap(6, 7) }
        btnSwap8.setOnClickListener { animateButton(it); swap(7, 8) }
        btnSwap9.setOnClickListener { animateButton(it); swap(8, 9) }
        btnSwap10.setOnClickListener { animateButton(it); swap(9, 10) }
        btnSwap11.setOnClickListener { animateButton(it); swap(10, 11) }
        btnSwap12.setOnClickListener { animateButton(it); swap(11, 12) }
        btnSwap13.setOnClickListener { animateButton(it); swap(12, 13) }
        btnSwap14.setOnClickListener { animateButton(it); swap(13, 14) }

        btnCheck.setOnClickListener {
            animateButton(it)
            if (numbers == numbers.sorted()) {
                score += 10
                updateScore()
                Toast.makeText(this, "Correct! You sorted all numbers.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not sorted yet. Keep going.", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            animateButton(it)
            generateNumbers()
            moves = 0
            score = 0
            updateNumbers()
            updateMoves()
            updateScore()
        }
    }

    private fun generateNumbers() {
        numbers = MutableList(15) { Random.nextInt(1, 100) }
    }

    private fun swap(i: Int, j: Int) {
        val temp = numbers[i]
        numbers[i] = numbers[j]
        numbers[j] = temp
        moves++
        score += 1
        updateNumbers()
        updateMoves()
        updateScore()
    }

    private fun updateNumbers() {
        val colors = listOf(
            "#F472B6",
            "#818CF8",
            "#38BDF8",
            "#34D399",
            "#FBBF24"
        )

        for (i in numberViews.indices) {
            val tv = numberViews[i]
            tv.text = numbers[i].toString()
            tv.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colors[i % colors.size]))

            tv.alpha = 0f
            tv.scaleX = 0.8f
            tv.scaleY = 0.8f
            tv.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(220)
                .start()
        }
    }

    private fun updateMoves() {
        tvMoves.text = "Moves: $moves"
    }

    private fun updateScore() {
        tvScore.text = "Score: $score"
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