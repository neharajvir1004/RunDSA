package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.QuizTopicAdapter
import com.rundsa.app.models.TopicModel

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)

        val titleText = findViewById<TextView>(R.id.titleText)
        titleText.text = "Crack The Quiz"

        val recycler = findViewById<RecyclerView>(R.id.topicRecycler)
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.isVerticalScrollBarEnabled = true

        val list = listOf(
            TopicModel("Arrays", R.drawable.ic_array, "Basics, introduction and more"),
            TopicModel("Linked List", R.drawable.ic_linked, "Nodes and connections"),
            TopicModel("Stack", R.drawable.ic_stack, "LIFO structure"),
            TopicModel("Queue", R.drawable.ic_queue, "FIFO structure"),
            TopicModel("Recursion", R.drawable.ic_recursion, "Function calling itself"),
            TopicModel("Sorting", R.drawable.ic_sort, "Arrange data efficiently"),
            TopicModel("Searching", R.drawable.ic_search, "Find elements quickly"),
            TopicModel("Trees", R.drawable.ic_tree, "Hierarchical structure"),
            TopicModel("Graphs", R.drawable.ic_graph, "Nodes and edges"),
            TopicModel("Dynamic Programming", R.drawable.ic_dp, "Optimization technique")
        )

        recycler.adapter = QuizTopicAdapter(list) { item ->
            val intent = Intent(this, QuizLevelActivity::class.java)
            intent.putExtra("TOPIC", item.title)
            startActivity(intent)
        }

        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }
    }
}