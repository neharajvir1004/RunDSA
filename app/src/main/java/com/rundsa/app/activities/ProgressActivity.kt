package com.rundsa.app.activities

import android.os.Bundle
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

        recyclerView = findViewById(R.id.progressRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

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
                ProgressModel(
                    topic = "📊 Quiz Results",
                    score = "",
                    runs = -1,
                    progress = -1
                )
            )

            // ================= QUIZ RESULTS =================
            db.collection("quiz_results")
                .whereEqualTo("userId", user.uid)
                .get()
                .addOnSuccessListener { quizDocs ->

                    if (!quizDocs.isEmpty) {
                        val uniqueMap = mutableMapOf<String, Pair<Long, Long>>() // key → score,total

                        for (doc in quizDocs) {
                            val topic = doc.getString("topic") ?: "Unknown"
                            val level = doc.getString("level") ?: ""
                            val score = doc.getLong("score") ?: 0L
                            val total = 10L

                            val key = "$topic-$level"

                            val existing = uniqueMap[key]

                            if (existing == null || score > existing.first) {
                                uniqueMap[key] = Pair(score, total)
                            }
                        }

                        // 🔥 ADD TO LIST (ONLY UNIQUE)
                        for ((key, value) in uniqueMap) {
                            val parts = key.split("-")
                            val topic = parts[0]
                            val level = parts[1]

                            list.add(
                                ProgressModel(
                                    topic = "$topic ($level)",
                                    score = "Score: ${value.first} / ${value.second}",
                                    runs = 0,
                                    progress = -1
                                )
                            )
                        }
                    }

                    // 🔥 HEADER 2: TOPIC PROGRESS
                    list.add(
                        ProgressModel(
                            topic = "📘 Topic Progress",
                            score = "",
                            runs = -1,
                            progress = -1
                        )
                    )

                    // ================= USER PROGRESS =================
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

                            recyclerView.adapter = ProgressAdapter(list)
                        }
                        .addOnFailureListener {
                            recyclerView.adapter = ProgressAdapter(emptyList())
                        }
                }

                // FAILURE
                .addOnFailureListener {
                    recyclerView.adapter = ProgressAdapter(emptyList())
                }

        } else {
            // USER NOT LOGGED IN
            recyclerView.adapter = ProgressAdapter(emptyList())
        }
    }
}