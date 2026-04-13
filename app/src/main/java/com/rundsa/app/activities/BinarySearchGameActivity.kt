package com.rundsa.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class BinarySearchGameActivity : AppCompatActivity() {

    private val numbers = listOf(2, 5, 8, 12, 16, 20, 25)
    private val target = 16

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binary_search_game)

        val tvArray = findViewById<TextView>(R.id.tvArray)
        val tvTarget = findViewById<TextView>(R.id.tvTarget)

        val btnIndex0 = findViewById<Button>(R.id.btnIndex0)
        val btnIndex1 = findViewById<Button>(R.id.btnIndex1)
        val btnIndex2 = findViewById<Button>(R.id.btnIndex2)
        val btnIndex3 = findViewById<Button>(R.id.btnIndex3)
        val btnIndex4 = findViewById<Button>(R.id.btnIndex4)
        val btnIndex5 = findViewById<Button>(R.id.btnIndex5)
        val btnIndex6 = findViewById<Button>(R.id.btnIndex6)

        tvArray.text = numbers.joinToString("   ")
        tvTarget.text = "Find target: $target"

        val buttons = listOf(btnIndex0, btnIndex1, btnIndex2, btnIndex3, btnIndex4, btnIndex5, btnIndex6)

        for (i in buttons.indices) {
            buttons[i].text = "Index $i"
            buttons[i].setOnClickListener {
                if (numbers[i] == target) {
                    Toast.makeText(this, "Correct! Target found at index $i", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Wrong index. Try again.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}