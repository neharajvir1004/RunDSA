package com.rundsa.app.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R

class PracticeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_detail)

        val titleText = findViewById<TextView>(R.id.titleText)
        val tvCode = findViewById<TextView>(R.id.tvCode)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)
        val btnRun = findViewById<Button>(R.id.btnRun)
        val backBtn = findViewById<ImageView>(R.id.backBtn)

        // Get data from previous screen
        val title = intent.getStringExtra("TITLE") ?: "Practice"
        val code = intent.getStringExtra("CODE") ?: "No code available"
        val output = intent.getStringExtra("OUTPUT") ?: "No output"

        // Set UI
        titleText.text = title
        tvCode.text = code
        tvOutput.visibility = View.GONE

        // ✅ ONLY KEEP THIS (no Firebase logic)
        btnRun.setOnClickListener {
            tvOutput.visibility = View.VISIBLE
            tvOutput.text = output
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}