package com.rundsa.app.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class SortingGameActivity : AppCompatActivity() {

    private var numbers = mutableListOf(5, 3, 8, 1, 4)
    private var moves = 0

    private lateinit var tvGoal: TextView
    private lateinit var tvMoves: TextView

    private lateinit var tvNum1: TextView
    private lateinit var tvNum2: TextView
    private lateinit var tvNum3: TextView
    private lateinit var tvNum4: TextView
    private lateinit var tvNum5: TextView

    private lateinit var btnSwap1: Button
    private lateinit var btnSwap2: Button
    private lateinit var btnSwap3: Button
    private lateinit var btnSwap4: Button
    private lateinit var btnCheck: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorting_game)

        tvGoal = findViewById(R.id.tvGoal)
        tvMoves = findViewById(R.id.tvMoves)

        tvNum1 = findViewById(R.id.tvNum1)
        tvNum2 = findViewById(R.id.tvNum2)
        tvNum3 = findViewById(R.id.tvNum3)
        tvNum4 = findViewById(R.id.tvNum4)
        tvNum5 = findViewById(R.id.tvNum5)

        btnSwap1 = findViewById(R.id.btnSwap1)
        btnSwap2 = findViewById(R.id.btnSwap2)
        btnSwap3 = findViewById(R.id.btnSwap3)
        btnSwap4 = findViewById(R.id.btnSwap4)
        btnCheck = findViewById(R.id.btnCheck)
        btnReset = findViewById(R.id.btnReset)

        tvGoal.text = "Sort the numbers in ascending order"

        updateNumbers()
        updateMoves()

        btnSwap1.setOnClickListener {
            animateButton(it)
            swap(0, 1)
        }

        btnSwap2.setOnClickListener {
            animateButton(it)
            swap(1, 2)
        }

        btnSwap3.setOnClickListener {
            animateButton(it)
            swap(2, 3)
        }

        btnSwap4.setOnClickListener {
            animateButton(it)
            swap(3, 4)
        }

        btnCheck.setOnClickListener {
            animateButton(it)

            if (numbers == numbers.sorted()) {
                Toast.makeText(this, "Correct! You sorted it in $moves moves.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not sorted yet. Keep going.", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            animateButton(it)
            numbers = mutableListOf(5, 3, 8, 1, 4)
            moves = 0
            updateNumbers()
            updateMoves()
        }
    }

    private fun swap(i: Int, j: Int) {
        val temp = numbers[i]
        numbers[i] = numbers[j]
        numbers[j] = temp
        moves++
        updateNumbers()
        updateMoves()
    }

    private fun updateNumbers() {
        setAnimatedNumber(tvNum1, numbers[0].toString())
        setAnimatedNumber(tvNum2, numbers[1].toString())
        setAnimatedNumber(tvNum3, numbers[2].toString())
        setAnimatedNumber(tvNum4, numbers[3].toString())
        setAnimatedNumber(tvNum5, numbers[4].toString())
    }

    private fun setAnimatedNumber(textView: TextView, value: String) {
        textView.alpha = 0f
        textView.scaleX = 0.8f
        textView.scaleY = 0.8f
        textView.text = value
        textView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(220)
            .start()
    }

    private fun updateMoves() {
        tvMoves.text = "Moves: $moves"
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