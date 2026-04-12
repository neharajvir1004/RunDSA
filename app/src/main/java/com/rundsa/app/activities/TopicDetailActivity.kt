package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rundsa.app.R
import com.rundsa.app.adapters.SubtypeAdapter
import com.rundsa.app.models.SubtypeModel
import com.rundsa.app.models.TopicDetailModel

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
    private var selectedSubtypeName: String = ""
    private var currentTopicName: String = ""

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

        currentTopicName = intent.getStringExtra("TOPIC") ?: "Stack"
        val topic = getTopicData(currentTopicName)

        tvTopicTitle.text = topic.title
        tvDefinition.text = topic.definition
        tvExample.text = topic.example

        if (topic.subtypes.isNotEmpty()) {
            layoutSubtypeSection.visibility = View.VISIBLE
            tvSubtypeHeading.text = when (topic.title) {
                "Sorting" -> "Types of Sorting"
                "Searching" -> "Types of Searching"
                else -> "Types"
            }

            selectedCode = ""
            selectedSubtypeName = ""
            tvCode.text = "Select any Type to try that code and view output of that code."

            rvSubtypes.layoutManager = LinearLayoutManager(this)
            rvSubtypes.adapter = SubtypeAdapter(topic.subtypes) { selectedSubtype ->
                selectedCode = selectedSubtype.code
                selectedSubtypeName = selectedSubtype.name
                tvCode.text = selectedSubtype.code
            }
        } else {
            layoutSubtypeSection.visibility = View.GONE
            selectedCode = topic.code
            selectedSubtypeName = ""
            tvCode.text = topic.code
        }

        btnBack.setOnClickListener {
            finish()
        }

        btnTryCode.setOnClickListener {
            if (selectedCode.isEmpty()) {
                Toast.makeText(this, "Please select a type first", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, PracticeCodeActivity::class.java)
                intent.putExtra("TOPIC", currentTopicName)
                intent.putExtra("SUBTYPE", selectedSubtypeName)
                startActivity(intent)
            }
        }
    }

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

            "Linked List" -> TopicDetailModel(
                title = "Linked List",
                definition = "A linked list is a linear data structure where each element is stored in a node, and each node points to the next node.",
                example = "Like train compartments connected one after another.",
                code = """
#include <stdio.h>
#include <stdlib.h>

struct Node {
    int data;
    struct Node* next;
};

int main() {
    struct Node* first = (struct Node*)malloc(sizeof(struct Node));
    struct Node* second = (struct Node*)malloc(sizeof(struct Node));

    first->data = 10;
    first->next = second;

    second->data = 20;
    second->next = NULL;

    printf("%d -> %d", first->data, second->data);

    return 0;
}
                """.trimIndent()
            )

            "Stack" -> TopicDetailModel(
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

            "Queue" -> TopicDetailModel(
                title = "Queue",
                definition = "A queue is a linear data structure that follows FIFO (First In First Out).",
                example = "People standing in a line.",
                code = """
#include <stdio.h>

int main() {
    int queue[5];
    int front = 0, rear = -1;

    rear++;
    queue[rear] = 10;

    rear++;
    queue[rear] = 20;

    printf("Front element: %d\n", queue[front]);

    front++;

    printf("New front after deletion: %d\n", queue[front]);

    return 0;
}
                """.trimIndent()
            )

            "Recursion" -> TopicDetailModel(
                title = "Recursion",
                definition = "Recursion is a process where a function calls itself to solve a smaller part of the problem.",
                example = "Factorial of a number.",
                code = """
#include <stdio.h>

int factorial(int n) {
    if (n == 1)
        return 1;
    else
        return n * factorial(n - 1);
}

int main() {
    int num = 5;
    printf("Factorial of %d is %d", num, factorial(num));
    return 0;
}
                """.trimIndent()
            )

            "Sorting" -> TopicDetailModel(
                title = "Sorting",
                definition = "Sorting is the process of arranging data in ascending or descending order.",
                example = "Arranging numbers like 5, 2, 1, 4 into 1, 2, 4, 5.",
                code = "",
                subtypes = listOf(
                    SubtypeModel(
                        name = "Bubble Sort",
                        definition = "Bubble sort compares adjacent elements and swaps them if they are in the wrong order.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {5, 3, 8, 1, 2};
    int i, j, temp;

    for(i = 0; i < 4; i++) {
        for(j = 0; j < 4 - i; j++) {
            if(arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }

    for(i = 0; i < 5; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Selection Sort",
                        definition = "Selection sort finds the smallest element and places it in the correct position.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {64, 25, 12, 22, 11};
    int i, j, min_idx, temp;

    for (i = 0; i < 4; i++) {
        min_idx = i;
        for (j = i + 1; j < 5; j++) {
            if (arr[j] < arr[min_idx]) {
                min_idx = j;
            }
        }
        temp = arr[min_idx];
        arr[min_idx] = arr[i];
        arr[i] = temp;
    }

    for (i = 0; i < 5; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}
                        """.trimIndent()
                    )
                )
            )

            "Searching" -> TopicDetailModel(
                title = "Searching",
                definition = "Searching is used to find an element in a data structure.",
                example = "Finding 10 in a list.",
                code = "",
                subtypes = listOf(
                    SubtypeModel(
                        name = "Linear Search",
                        definition = "Linear search checks elements one by one.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {4, 8, 1, 9, 3};
    int key = 9, i, found = 0;

    for(i = 0; i < 5; i++) {
        if(arr[i] == key) {
            printf("Element found at position %d", i + 1);
            found = 1;
            break;
        }
    }

    if(!found) {
        printf("Element not found");
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Binary Search",
                        definition = "Binary search works on sorted arrays by dividing the search space into halves.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {1, 3, 5, 7, 9};
    int key = 7, low = 0, high = 4, mid;

    while(low <= high) {
        mid = (low + high) / 2;

        if(arr[mid] == key) {
            printf("Element found at position %d", mid + 1);
            return 0;
        } else if(arr[mid] < key) {
            low = mid + 1;
        } else {
            high = mid - 1;
        }
    }

    printf("Element not found");
    return 0;
}
                        """.trimIndent()
                    )
                )
            )

            "Trees" -> TopicDetailModel(
                title = "Trees",
                definition = "A tree is a hierarchical data structure with nodes connected by edges.",
                example = "Root, left child, right child.",
                code = """
#include <stdio.h>

int main() {
    printf("Root -> Left Child -> Right Child");
    return 0;
}
                """.trimIndent()
            )

            "Graphs" -> TopicDetailModel(
                title = "Graphs",
                definition = "A graph is a non-linear data structure made of nodes and edges.",
                example = "Cities connected by roads.",
                code = """
#include <stdio.h>

int main() {
    printf("Graph = Vertices + Edges");
    return 0;
}
                """.trimIndent()
            )

            "Dynamic Programming" -> TopicDetailModel(
                title = "Dynamic Programming",
                definition = "Dynamic programming solves problems by storing results of smaller subproblems.",
                example = "Fibonacci using DP.",
                code = """
#include <stdio.h>

int main() {
    int n = 6;
    int dp[6];
    dp[0] = 0;
    dp[1] = 1;

    for(int i = 2; i < n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    for(int i = 0; i < n; i++) {
        printf("%d ", dp[i]);
    }

    return 0;
}
                """.trimIndent()
            )

            else -> TopicDetailModel(
                title = "Stack",
                definition = "A stack is a linear data structure that follows LIFO.",
                example = "Stack of plates.",
                code = """
#include <stdio.h>
int main() {
    printf("Stack Example");
    return 0;
}
                """.trimIndent()
            )
        }
    }
}