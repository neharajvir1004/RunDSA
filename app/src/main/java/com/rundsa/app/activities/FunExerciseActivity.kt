package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class FunExerciseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fun_exercise)

        val btnSortingGame = findViewById<Button>(R.id.btnSortingGame)
        val btnBinarySearch = findViewById<Button>(R.id.btnBinarySearch)
        val btnMemory = findViewById<Button>(R.id.btnMemoryGame)
        val btnReverse = findViewById<Button>(R.id.btnReverseGame)

        btnSortingGame.setOnClickListener {
            startActivity(Intent(this, SortingGameActivity::class.java))
        }

        btnBinarySearch.setOnClickListener {
            startActivity(Intent(this, BinarySearchGameActivity::class.java))
        }

        btnMemory.setOnClickListener {
            startActivity(Intent(this, MemoryGameActivity::class.java))
        }

        btnReverse.setOnClickListener {
            startActivity(Intent(this, ReverseGameActivity::class.java))
        }
    }
}