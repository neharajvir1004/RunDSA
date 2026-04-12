package com.rundsa.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class SortingGameActivity : AppCompatActivity() {

    private var numbers = mutableListOf(5, 3, 8, 1, 4)
    private lateinit var tvNumbers: TextView
    private lateinit var tvGoal: TextView
    private lateinit var btnSwap1: Button
    private lateinit var btnSwap2: Button
    private lateinit var btnCheck: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sorting_game)

        tvNumbers = findViewById(R.id.tvNumbers)
        tvGoal = findViewById(R.id.tvGoal)
        btnSwap1 = findViewById(R.id.btnSwap1)
        btnSwap2 = findViewById(R.id.btnSwap2)
        btnCheck = findViewById(R.id.btnCheck)
        btnReset = findViewById(R.id.btnReset)

        tvGoal.text = "Goal: Sort the numbers in ascending order"

        updateNumbers()

        btnSwap1.setOnClickListener {
            swap(0, 1)
        }

        btnSwap2.setOnClickListener {
            swap(1, 2)
        }

        btnCheck.setOnClickListener {
            if (numbers == numbers.sorted()) {
                Toast.makeText(this, "Correct! Array is sorted.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Not sorted yet. Try again.", Toast.LENGTH_SHORT).show()
            }
        }

        btnReset.setOnClickListener {
            numbers = mutableListOf(5, 3, 8, 1, 4)
            updateNumbers()
        }
    }

    private fun swap(i: Int, j: Int) {
        val temp = numbers[i]
        numbers[i] = numbers[j]
        numbers[j] = temp
        updateNumbers()
    }

    private fun updateNumbers() {
        tvNumbers.text = numbers.joinToString("   ")
    }
}