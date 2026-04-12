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
        val topic = getTopicData(topicName)

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
                val intent = Intent(this, PracticeCodeActivity::class.java)
                intent.putExtra("CODE", selectedCode)
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
    int arr[5] = {4, 2, 7, 1, 3};
    int i, j, min, temp;

    for(i = 0; i < 4; i++) {
        min = i;
        for(j = i + 1; j < 5; j++) {
            if(arr[j] < arr[min]) {
                min = j;
            }
        }
        temp = arr[i];
        arr[i] = arr[min];
        arr[min] = temp;
    }

    for(i = 0; i < 5; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Insertion Sort",
                        definition = "Insertion sort takes one element at a time and inserts it into its correct position.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {5, 2, 4, 1, 3};
    int i, j, key;

    for(i = 1; i < 5; i++) {
        key = arr[i];
        j = i - 1;

        while(j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }

        arr[j + 1] = key;
    }

    for(i = 0; i < 5; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Merge Sort",
                        definition = "Merge sort divides the array into smaller parts, sorts them, and then merges them. Simple idea: Divide -> Sort -> Merge.",
                        code = """
#include <stdio.h>

void merge(int arr[], int left, int mid, int right) {
    int i, j, k;
    int n1 = mid - left + 1;
    int n2 = right - mid;

    int L[50], R[50];

    for(i = 0; i < n1; i++)
        L[i] = arr[left + i];
    for(j = 0; j < n2; j++)
        R[j] = arr[mid + 1 + j];

    i = 0;
    j = 0;
    k = left;

    while(i < n1 && j < n2) {
        if(L[i] <= R[j]) {
            arr[k] = L[i];
            i++;
        } else {
            arr[k] = R[j];
            j++;
        }
        k++;
    }

    while(i < n1) {
        arr[k] = L[i];
        i++;
        k++;
    }

    while(j < n2) {
        arr[k] = R[j];
        j++;
        k++;
    }
}

void mergeSort(int arr[], int left, int right) {
    if(left < right) {
        int mid = (left + right) / 2;

        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);

        merge(arr, left, mid, right);
    }
}

int main() {
    int arr[5] = {5, 2, 4, 1, 3};
    int i;

    mergeSort(arr, 0, 4);

    for(i = 0; i < 5; i++) {
        printf("%d ", arr[i]);
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Quick Sort",
                        definition = "Quick sort selects a pivot element and places smaller elements on one side and larger elements on the other side.",
                        code = """
#include <stdio.h>

int partition(int arr[], int low, int high) {
    int pivot = arr[high];
    int i = low - 1;
    int j, temp;

    for(j = low; j < high; j++) {
        if(arr[j] < pivot) {
            i++;
            temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    temp = arr[i + 1];
    arr[i + 1] = arr[high];
    arr[high] = temp;

    return i + 1;
}

void quickSort(int arr[], int low, int high) {
    if(low < high) {
        int pi = partition(arr, low, high);

        quickSort(arr, low, pi - 1);
        quickSort(arr, pi + 1, high);
    }
}

int main() {
    int arr[5] = {5, 2, 4, 1, 3};
    int i;

    quickSort(arr, 0, 4);

    for(i = 0; i < 5; i++) {
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
                definition = "Searching means finding a specific element in a collection of data.",
                example = "Finding number 20 in an array.",
                code = "",
                subtypes = listOf(
                    SubtypeModel(
                        name = "Linear Search",
                        definition = "Checks each element one by one.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {10, 20, 30, 40, 50};
    int i, key = 30;

    for(i = 0; i < 5; i++) {
        if(arr[i] == key) {
            printf("Element found at position %d", i);
            break;
        }
    }

    return 0;
}
                        """.trimIndent()
                    ),
                    SubtypeModel(
                        name = "Binary Search",
                        definition = "Binary search works only on sorted arrays and checks the middle element first.",
                        code = """
#include <stdio.h>

int main() {
    int arr[5] = {10, 20, 30, 40, 50};
    int low = 0, high = 4, mid, key = 40;

    while(low <= high) {
        mid = (low + high) / 2;

        if(arr[mid] == key) {
            printf("Element found at position %d", mid);
            break;
        }
        else if(arr[mid] < key) {
            low = mid + 1;
        }
        else {
            high = mid - 1;
        }
    }

    return 0;
}
                        """.trimIndent()
                    )
                )
            )

            "Trees" -> TopicDetailModel(
                title = "Trees",
                definition = "A tree is a hierarchical data structure made of nodes connected by edges.",
                example = "Family tree or folder structure in a computer.",
                code = """
#include <stdio.h>
#include <stdlib.h>

struct Node {
    int data;
    struct Node* left;
    struct Node* right;
};

int main() {
    struct Node* root = (struct Node*)malloc(sizeof(struct Node));
    root->data = 10;

    root->left = (struct Node*)malloc(sizeof(struct Node));
    root->left->data = 5;
    root->left->left = NULL;
    root->left->right = NULL;

    root->right = (struct Node*)malloc(sizeof(struct Node));
    root->right->data = 20;
    root->right->left = NULL;
    root->right->right = NULL;

    printf("Root: %d\n", root->data);
    printf("Left Child: %d\n", root->left->data);
    printf("Right Child: %d\n", root->right->data);

    return 0;
}
                """.trimIndent()
            )

            "Graphs" -> TopicDetailModel(
                title = "Graphs",
                definition = "A graph is a non-linear data structure made of vertices (nodes) and edges (connections).",
                example = "Cities connected by roads.",
                code = """
#include <stdio.h>

int main() {
    int graph[3][3] = {
        {0, 1, 1},
        {1, 0, 1},
        {1, 1, 0}
    };

    printf("Connection between 0 and 1: %d\n", graph[0][1]);
    printf("Connection between 1 and 2: %d\n", graph[1][2]);

    return 0;
}
                """.trimIndent()
            )

            "Dynamic Programming" -> TopicDetailModel(
                title = "Dynamic Programming",
                definition = "Dynamic programming is a method used to solve problems by breaking them into smaller subproblems and storing their answers to avoid repeated work.",
                example = "Fibonacci series.",
                code = """
#include <stdio.h>

int main() {
    int n = 6;
    int dp[10];
    int i;

    dp[0] = 0;
    dp[1] = 1;

    for(i = 2; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }

    printf("Fibonacci of %d is %d", n, dp[n]);

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