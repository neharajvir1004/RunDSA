package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.SubtypeAdapter
import com.rundsa.app.models.SubtypeModel
import com.rundsa.app.models.TopicDetailModel

// 🔥 FIREBASE
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class TopicDetailActivity : AppCompatActivity() {

    private lateinit var tvTopicTitle: TextView
    private lateinit var tvDefinition: TextView
    private lateinit var tvExample: TextView
    private lateinit var tvCode: TextView
    private lateinit var btnTryCode: Button
    private lateinit var btnBack: ImageView
    private lateinit var layoutSubtypeSection: LinearLayout
    private lateinit var tvSubtypeHeading: TextView
    private lateinit var rvSubtypes: RecyclerView

    private var selectedCode: String = ""

    // 🔥 GLOBAL
    private lateinit var topicNameGlobal: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topic_detail)

        tvTopicTitle = findViewById(R.id.tvTopicTitle)
        tvDefinition = findViewById(R.id.tvDefinition)
        tvExample = findViewById(R.id.tvExample)
        tvCode = findViewById(R.id.tvCode)
        btnTryCode = findViewById(R.id.btnTryCode)
        btnBack = findViewById(R.id.btnBack)
        layoutSubtypeSection = findViewById(R.id.layoutSubtypeSection)
        tvSubtypeHeading = findViewById(R.id.tvSubtypeHeading)
        rvSubtypes = findViewById(R.id.rvSubtypes)

        val topicName = intent.getStringExtra("TOPIC") ?: "Stack"
        topicNameGlobal = topicName

        val topic = getTopicData(topicName)

        tvTopicTitle.text = topic.title
        tvDefinition.text = topic.definition
        tvExample.text = topic.example

        // 🔥 MARK VIEWED
        markTopicViewed(topic.title)

        if (topic.subtypes.isNotEmpty()) {
            layoutSubtypeSection.visibility = View.VISIBLE

            // 🔥 MARK SUBTYPES
            markAllSubtypesCompleted(topic.title, topic.subtypes)

            tvSubtypeHeading.text = when (topic.title) {
                "Sorting" -> "Types of Sorting"
                "Searching" -> "Types of Searching"
                else -> "Types"
            }

            selectedCode = ""
            tvCode.text = "Select any Type to try that code and view output of that code."

            rvSubtypes.layoutManager = LinearLayoutManager(this)
            rvSubtypes.adapter = SubtypeAdapter(topic.subtypes) { selectedSubtype ->
                selectedCode = selectedSubtype.code
                tvCode.text = selectedSubtype.code
            }

        } else {
            layoutSubtypeSection.visibility = View.GONE
            selectedCode = topic.code
            tvCode.text = topic.code
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnTryCode.setOnClickListener {
            if (selectedCode.isEmpty()) {
                Toast.makeText(this, "Please select a type first", Toast.LENGTH_SHORT).show()
            } else {

                // 🔥 TRACK RUN
                updateRunCount(topicNameGlobal)

                val intent = Intent(this, PracticeCodeActivity::class.java)
                intent.putExtra("CODE", selectedCode)
                startActivity(intent)
            }
        }
    }

    // ================= FIREBASE =================

    private fun markTopicViewed(topic: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        val docId = "${user.uid}_$topic"

        val data = hashMapOf(
            "userId" to user.uid,
            "email" to user.email,   // ✅ ADDED
            "topic" to topic,
            "viewed" to true,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("user_progress")
            .document(docId)
            .set(data, SetOptions.merge())
    }

    private fun updateRunCount(topic: String) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        val docId = "${user.uid}_$topic"
        val docRef = db.collection("user_progress").document(docId)

        docRef.get().addOnSuccessListener { document ->
            val currentCount = document.getLong("codeRunCount") ?: 0

            docRef.set(
                mapOf(
                    "codeRunCount" to currentCount + 1,
                    "email" to user.email   // ✅ ADDED
                ),
                SetOptions.merge()
            )
        }
    }

    private fun markAllSubtypesCompleted(topic: String, subtypes: List<SubtypeModel>) {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val db = FirebaseFirestore.getInstance()

        val docId = "${user.uid}_$topic"

        val subtypeNames = subtypes.map { it.name }

        db.collection("user_progress")
            .document(docId)
            .set(
                mapOf(
                    "subtopicsCompleted" to subtypeNames,
                    "email" to user.email   // ✅ ADDED
                ),
                SetOptions.merge()
            )
    }

    // ================= DATA =================

    private fun getTopicData(topicName: String): TopicDetailModel {
        return when (topicName) {

            "Arrays" -> TopicDetailModel(
                title = "Arrays",
                definition = "An array is a collection of elements of the same data type stored in contiguous memory locations.",
                example = "A list of marks: 10, 20, 30, 40",
                code = """
#include <stdio.h>

int main() {
    int arr[4] = {10, 20, 30, 40};

    printf("First element: %d\n", arr[0]);
    printf("Third element: %d\n", arr[2]);

    return 0;
}
                """.trimIndent()
            )

            else -> TopicDetailModel(
                title = "Stack",
                definition = "A stack is a linear data structure that follows LIFO (Last In First Out).",
                example = "Stack of plates.",
                code = """
#include <stdio.h>

int main() {
    int stack[5];
    int top = -1;

    top++;
    stack[top] = 10;

    top++;
    stack[top] = 20;

    printf("Top element before pop: %d\n", stack[top]);

    top--;

    printf("Top after pop: %d\n", stack[top]);

    return 0;
}
                """.trimIndent()
            )
        }
    }
}