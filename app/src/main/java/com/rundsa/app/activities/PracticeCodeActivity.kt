package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.PracticeTopicAdapter
import com.rundsa.app.models.PracticeTopicModel

class PracticeCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practice_code)

        val titleText = findViewById<TextView>(R.id.titleText)
        val btnOnlineCompiler = findViewById<Button>(R.id.btnOnlineCompiler)
        val recycler = findViewById<RecyclerView>(R.id.topicRecycler)
        val backBtn = findViewById<ImageView>(R.id.backBtn)

        titleText.text = "Practice Code"

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.isVerticalScrollBarEnabled = true

        val list = listOf(
            PracticeTopicModel(
                "Arrays",
                R.drawable.ic_array,
                "Basic array print example",
                """
                #include <stdio.h>

                int main() {
                    int arr[] = {10, 20, 30, 40, 50};
                    int i;
                    for(i = 0; i < 5; i++) {
                        printf("%d ", arr[i]);
                    }
                    return 0;
                }
                """.trimIndent(),
                "10 20 30 40 50"
            ),
            PracticeTopicModel(
                "Searching",
                R.drawable.ic_search,
                "Linear search example",
                """
                #include <stdio.h>

                int main() {
                    int arr[] = {5, 8, 12, 20, 25};
                    int key = 12;
                    int i, found = 0;

                    for(i = 0; i < 5; i++) {
                        if(arr[i] == key) {
                            printf("Element found at index: %d", i);
                            found = 1;
                            break;
                        }
                    }

                    if(found == 0) {
                        printf("Element not found");
                    }

                    return 0;
                }
                """.trimIndent(),
                "Element found at index: 2"
            ),
            PracticeTopicModel(
                "Stack",
                R.drawable.ic_stack,
                "Simple stack example",
                """
                #include <stdio.h>

                int main() {
                    int stack[3] = {10, 20, 30};

                    printf("Top element: %d\n", stack[2]);
                    printf("Stack elements: 10 20 30");

                    return 0;
                }
                """.trimIndent(),
                "Top element: 30\nStack elements: 10 20 30"
            ),
            PracticeTopicModel(
                "Queue",
                R.drawable.ic_queue,
                "Simple queue example",
                """
                #include <stdio.h>

                int main() {
                    int queue[3] = {10, 20, 30};

                    printf("Front element: %d\n", queue[0]);
                    printf("Queue elements: 10 20 30");

                    return 0;
                }
                """.trimIndent(),
                "Front element: 10\nQueue elements: 10 20 30"
            )
        )

        recycler.adapter = PracticeTopicAdapter(list) { item ->
            val intent = Intent(this, PracticeDetailActivity::class.java)
            intent.putExtra("TITLE", item.title)
            intent.putExtra("CODE", item.code)
            intent.putExtra("OUTPUT", item.output)
            startActivity(intent)
        }

        btnOnlineCompiler.setOnClickListener {
            startActivity(Intent(this, CompilerActivity::class.java))
        }

        backBtn.setOnClickListener {
            finish()
        }
    }
}