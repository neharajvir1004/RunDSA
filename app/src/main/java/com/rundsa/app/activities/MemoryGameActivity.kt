package com.rundsa.app.activities

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R
import kotlin.random.Random

class MemoryGameActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvLevel: TextView
    private lateinit var tvInstruction: TextView
    private lateinit var btnNext: Button
    private lateinit var btnReset: Button

    private lateinit var boxViews: List<TextView>

    private val pattern = mutableListOf<Int>()
    private val userPattern = mutableListOf<Int>()

    private var level = 1
    private var isShowingPattern = false

    private val normalColor = Color.parseColor("#A7F3D0")   // light green
    private val activeColor = Color.parseColor("#8B5E3C")   // brown
    private val correctColor = Color.parseColor("#16A34A")  // green
    private val wrongColor = Color.parseColor("#DC2626")    // red

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_game)

        tvTitle = findViewById(R.id.tvTitle)
        tvLevel = findViewById(R.id.tvLevel)
        tvInstruction = findViewById(R.id.tvInstruction)
        btnNext = findViewById(R.id.btnNext)
        btnReset = findViewById(R.id.btnReset)

        boxViews = listOf(
            findViewById(R.id.box0),
            findViewById(R.id.box1),
            findViewById(R.id.box2),
            findViewById(R.id.box3),
            findViewById(R.id.box4),
            findViewById(R.id.box5),
            findViewById(R.id.box6),
            findViewById(R.id.box7),
            findViewById(R.id.box8),
            findViewById(R.id.box9),
            findViewById(R.id.box10),
            findViewById(R.id.box11),
            findViewById(R.id.box12),
            findViewById(R.id.box13),
            findViewById(R.id.box14)
        )

        setupBoxes()
        startLevel()

        btnNext.setOnClickListener {
            if (!isShowingPattern) {
                level++
                startLevel()
            }
        }

        btnReset.setOnClickListener {
            level = 1
            startLevel()
        }
    }

    private fun setupBoxes() {
        for (i in boxViews.indices) {
            boxViews[i].text = (i + 1).toString()
            setBoxColor(boxViews[i], normalColor)

            boxViews[i].setOnClickListener {
                if (isShowingPattern) return@setOnClickListener
                handleUserTap(i)
            }
        }
    }

    private fun startLevel() {
        pattern.clear()
        userPattern.clear()

        tvLevel.text = "Level: $level"
        tvInstruction.text = "Watch the DSA pattern"

        resetAllBoxes()

        val patternLength = level + 2
        repeat(patternLength) {
            pattern.add(Random.nextInt(0, 15))
        }

        showPattern()
    }

    private fun showPattern() {
        isShowingPattern = true
        userPattern.clear()

        val handler = Handler(Looper.getMainLooper())
        var delay = 500L

        for (index in pattern) {
            handler.postDelayed({
                highlightBox(index)
            }, delay)

            handler.postDelayed({
                resetBox(index)
            }, delay + 500)

            delay += 800
        }

        handler.postDelayed({
            isShowingPattern = false
            tvInstruction.text = "Tap the boxes in the same order"
        }, delay)
    }

    private fun highlightBox(index: Int) {
        val box = boxViews[index]
        setBoxColor(box, activeColor)
        box.animate()
            .scaleX(1.12f)
            .scaleY(1.12f)
            .setDuration(180)
            .withEndAction {
                box.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(180)
                    .start()
            }
            .start()
    }

    private fun resetBox(index: Int) {
        setBoxColor(boxViews[index], normalColor)
    }

    private fun resetAllBoxes() {
        for (box in boxViews) {
            setBoxColor(box, normalColor)
            box.scaleX = 1f
            box.scaleY = 1f
        }
    }

    private fun handleUserTap(index: Int) {
        val currentStep = userPattern.size
        userPattern.add(index)

        highlightBox(index)
        Handler(Looper.getMainLooper()).postDelayed({
            resetBox(index)
        }, 250)

        if (pattern[currentStep] != index) {
            setBoxColor(boxViews[index], wrongColor)
            tvInstruction.text = "Wrong pattern! Try again."
            Toast.makeText(this, "Wrong pattern!", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                startLevel()
            }, 1000)
            return
        }

        if (userPattern.size == pattern.size) {
            tvInstruction.text = "Correct! Tap Next Level"
            Toast.makeText(this, "Correct pattern! Great job.", Toast.LENGTH_SHORT).show()

            for (i in pattern) {
                setBoxColor(boxViews[i], correctColor)
            }
        }
    }

    private fun setBoxColor(box: TextView, color: Int) {
        box.backgroundTintList = ColorStateList.valueOf(color)
    }
}