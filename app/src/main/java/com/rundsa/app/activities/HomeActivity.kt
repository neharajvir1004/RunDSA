package com.rundsa.app.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.FeatureAdapter
import com.rundsa.app.models.FeatureModel

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val username = intent.getStringExtra("USERNAME") ?: "User"

        val helloText = findViewById<TextView>(R.id.txtHello)
        helloText.text = "Hello, $username!"

        val recycler = findViewById<RecyclerView>(R.id.featureRecycler)

        val featureList = listOf(

            FeatureModel(
                "Learn DSA",
                R.drawable.ic_book,
                R.drawable.card_gradient_dsa
            ),

            FeatureModel(
                "Quizzes",
                R.drawable.ic_quiz,
                R.drawable.card_gradient_quiz
            ),

            FeatureModel(
                "Practice Code",
                R.drawable.ic_code,
                R.drawable.card_gradient_code
            ),

            FeatureModel(
                "Fun Exercise",
                R.drawable.ic_game,
                R.drawable.card_gradient_fun
            ),

            FeatureModel(
                "Progress",
                R.drawable.ic_progress,
                R.drawable.card_gradient_progress
            )
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FeatureAdapter(featureList)
    }
}