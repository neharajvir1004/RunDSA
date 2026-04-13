package com.rundsa.app.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class ReverseGameActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvLevel: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var tvScore: TextView
    private lateinit var tvMoves: TextView
    private lateinit var btnCheck: Button
    private lateinit var btnReset: Button

    private lateinit var itemViews: List<TextView>

    private val levels = listOf(
        listOf("Array", "String", "Linked List", "Stack", "Queue"),
        listOf("Sorting", "Searching", "Recursion", "Hashing", "Tree"),
        listOf("Graph", "BFS", "DFS", "Dynamic Programming", "Greedy")
    )

    private var currentItems = mutableListOf<String>()
    private var firstSelectedIndex = -1
    private var currentLevelIndex = 0
    private var score = 0
    private var moves = 0

    private val normalColor = Color.parseColor("#F59E0B")   // orange
    private val selectedColor = Color.parseColor("#8B5E3C") // brown
    private val correctColor = Color.parseColor("#16A34A")  // green

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reverse_game)

        tvTitle = findViewById(R.id.tvTitle)
        tvLevel = findViewById(R.id.tvLevel)
        tvInstruction = findViewById(R.id.tvInstruction)
        tvScore = findViewById(R.id.tvScore)
        tvMoves = findViewById(R.id.tvMoves)
        btnCheck = findViewById(R.id.btnCheck)
        btnReset = findViewById(R.id.btnReset)

        itemViews = listOf(
            findViewById(R.id.item0),
            findViewById(R.id.item1),
            findViewById(R.id.item2),
            findViewById(R.id.item3),
            findViewById(R.id.item4)
        )

        setupItems()

        tvTitle.text = "DSA Arrange Game"
        btnCheck.setOnClickListener { checkAnswer() }
        btnReset.setOnClickListener { resetGame() }

        loadLevel()
    }

    private fun setupItems() {
        for (i in itemViews.indices) {
            val itemView = itemViews[i]
            setBoxColor(itemView, normalColor)

            itemView.setOnClickListener {
                handleSelection(i)
            }
        }
    }

    private fun loadLevel() {
        val currentLevel = levels[currentLevelIndex]

        currentItems = currentLevel.shuffled().toMutableList()
        firstSelectedIndex = -1
        moves = 0

        tvLevel.text = "Level ${currentLevelIndex + 1}"
        tvInstruction.text = "Tap 2 boxes to swap and arrange them in correct order"
        tvMoves.text = "Moves: $moves"
        tvScore.text = "Score: $score"

        renderItems()
    }

    private fun renderItems() {
        for (i in itemViews.indices) {
            val itemView = itemViews[i]
            itemView.text = currentItems[i]
            setBoxColor(itemView, normalColor)

            itemView.alpha = 0f
            itemView.scaleX = 0.9f
            itemView.scaleY = 0.9f
            itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(180)
                .start()
        }
    }

    private fun handleSelection(index: Int) {
        if (firstSelectedIndex == -1) {
            firstSelectedIndex = index
            setBoxColor(itemViews[index], selectedColor)
        } else if (firstSelectedIndex == index) {
            setBoxColor(itemViews[index], normalColor)
            firstSelectedIndex = -1
        } else {
            swapItems(firstSelectedIndex, index)
            firstSelectedIndex = -1
            moves++
            tvMoves.text = "Moves: $moves"
            renderItems()
        }
    }

    private fun swapItems(i: Int, j: Int) {
        val temp = currentItems[i]
        currentItems[i] = currentItems[j]
        currentItems[j] = temp
    }

    private fun checkAnswer() {
        val correctOrder = levels[currentLevelIndex]

        if (currentItems == correctOrder) {
            score += 10
            tvScore.text = "Score: $score"
            tvInstruction.text = "Correct! Moving to next level..."

            for (item in itemViews) {
                setBoxColor(item, correctColor)
            }

            Toast.makeText(this, "Level ${currentLevelIndex + 1} Complete 🎉", Toast.LENGTH_SHORT).show()

            itemViews[0].postDelayed({
                if (currentLevelIndex < levels.size - 1) {
                    currentLevelIndex++
                    loadLevel()
                } else {
                    tvInstruction.text = "All levels completed! Great job 🔥"
                    Toast.makeText(this, "Game Finished! Final Score: $score", Toast.LENGTH_LONG).show()
                }
            }, 1200)

        } else {
            tvInstruction.text = "Not correct yet. Keep arranging."
            Toast.makeText(this, "Wrong order. Try again.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetGame() {
        score = 0
        currentLevelIndex = 0
        loadLevel()
        Toast.makeText(this, "Game reset.", Toast.LENGTH_SHORT).show()
    }

    private fun setBoxColor(view: TextView, color: Int) {
        view.backgroundTintList = ColorStateList.valueOf(color)
    }
}