package com.rundsa.app.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.rundsa.app.R
import com.rundsa.app.adapters.ProgressAdapter
import com.rundsa.app.models.ProgressModel


class ProgressActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)

        val backBtn = findViewById<ImageView>(R.id.backBtn)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        recyclerView = findViewById(R.id.progressRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setPadding(0, 4, 0, 4)
        recyclerView.clipToPadding = false

        loadProgress()
    }

    // 🔥 CALCULATE PROGRESS
    private fun calculateProgress(
        viewed: Boolean,
        runCount: Long
    ): Int {

        var progress = 0

        if (viewed) progress += 50
        if (runCount > 0) progress += 50

        return progress
    }

    // 🔥 Fetch data from Firebase
    private fun loadProgress() {

        val db = FirebaseFirestore.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {

            val list = mutableListOf<ProgressModel>()

            // 🔥 HEADER 1: QUIZ RESULTS
            list.add(
                ProgressModel("📊 Quiz Results", "", -1, -1)
            )

            db.collection("quiz_results")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { quizDocs ->

                    if (!quizDocs.isEmpty) {

                        val bestScoreMap = mutableMapOf<String, Long>()

                        for (doc in quizDocs) {
                            val topic = doc.getString("topic") ?: "Unknown"
                            val level = doc.getString("level") ?: ""
                            val score = doc.getLong("score") ?: 0L

                            val key = "$topic-$level"

                            val existing = bestScoreMap[key]

                            if (existing == null || score > existing) {
                                bestScoreMap[key] = score
                            }
                        }

// 🔥 ADD BEST SCORES ONLY
                        for ((key, bestScore) in bestScoreMap) {
                            val parts = key.split("-")
                            val topic = parts[0]
                            val level = parts[1]

                            list.add(
                                ProgressModel(
                                    topic = "$topic ($level)",   // ✅ correct topic
                                    score = "Score: $bestScore / 10",  // ✅ correct format
                                    runs = 0,
                                    progress = -1
                                )
                            )
                        }
                    }

                    // 🔥 HEADER 2: TOPIC PROGRESS
                    list.add(
                        ProgressModel("📘 Topic Progress", "", -1, -1)
                    )

                    db.collection("user_progress")
                        .whereEqualTo("userId", user.uid)
                        .get()
                        .addOnSuccessListener { progressDocs ->

                            if (!progressDocs.isEmpty) {

                                for (doc in progressDocs) {

                                    val topic = doc.getString("topic") ?: "Unknown"
                                    val viewed = doc.getBoolean("viewed") ?: false
                                    val runCount = doc.getLong("codeRunCount") ?: 0

                                    val progress = calculateProgress(
                                        viewed,
                                        runCount
                                    )

                                    list.add(
                                        ProgressModel(
                                            topic = topic,
                                            score = "",
                                            runs = runCount,
                                            progress = progress
                                        )
                                    )
                                }
                            }

                            // ✅ FINAL ADAPTER HERE
                            recyclerView.adapter = ProgressAdapter(list)
                        }

                        .addOnFailureListener {
                            recyclerView.adapter = ProgressAdapter(emptyList())
                        }
                }

                .addOnFailureListener {
                    recyclerView.adapter = ProgressAdapter(emptyList())
                }

        } else {
            recyclerView.adapter = ProgressAdapter(emptyList())
        }
    }

    override fun onResume() {
        super.onResume()
        loadProgress()
    }
}