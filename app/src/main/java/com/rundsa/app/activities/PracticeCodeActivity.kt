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
                "Basic array",
                """
#include <stdio.h>
int main() {
    int arr[] = {1,2,3,4,5};
    for(int i=0;i<5;i++) printf("%d ", arr[i]);
    return 0;
}
                """.trimIndent(),
                "1 2 3 4 5"
            ),

            PracticeTopicModel(
                "Linked List",
                R.drawable.ic_linked,
                "Simple linked list",
                """
#include <stdio.h>
struct node {
    int data;
    struct node* next;
};
int main(){
    struct node n1, n2;
    n1.data=10;
    n2.data=20;
    n1.next=&n2;
    printf("%d %d", n1.data, n1.next->data);
    return 0;
}
                """.trimIndent(),
                "10 20"
            ),

            PracticeTopicModel(
                "Stack",
                R.drawable.ic_stack,
                "Stack example",
                """
#include <stdio.h>
int main() {
    int stack[3]={10,20,30};
    printf("Top: %d", stack[2]);
    return 0;
}
                """.trimIndent(),
                "Top: 30"
            ),

            PracticeTopicModel(
                "Queue",
                R.drawable.ic_queue,
                "Queue example",
                """
#include <stdio.h>
int main() {
    int q[3]={10,20,30};
    printf("Front: %d", q[0]);
    return 0;
}
                """.trimIndent(),
                "Front: 10"
            ),

            PracticeTopicModel(
                "Recursion",
                R.drawable.ic_recursion,
                "Factorial",
                """
#include <stdio.h>
int fact(int n){
    if(n==0) return 1;
    return n*fact(n-1);
}
int main(){
    printf("%d", fact(5));
    return 0;
}
                """.trimIndent(),
                "120"
            ),

            PracticeTopicModel(
                "Bubble Sort",
                R.drawable.ic_sort,
                "Bubble Sort",
                """
#include <stdio.h>
int main(){
    int arr[]={5,2,9,1};
    for(int i=0;i<4;i++)
        for(int j=0;j<3;j++)
            if(arr[j]>arr[j+1]){
                int t=arr[j];
                arr[j]=arr[j+1];
                arr[j+1]=t;
            }
    for(int i=0;i<4;i++) printf("%d ",arr[i]);
    return 0;
}
                """.trimIndent(),
                "1 2 5 9"
            ),

            PracticeTopicModel(
                "Selection Sort",
                R.drawable.ic_sort,
                "Selection Sort",
                """
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
                """.trimIndent(),
                "11 12 22 25 64"
            ),

            PracticeTopicModel(
                "Linear Search",
                R.drawable.ic_search,
                "Linear Search",
                """
#include <stdio.h>
int main() {
    int arr[]={1,2,3,4,5}, key=3;
    for(int i=0;i<5;i++){
        if(arr[i]==key){
            printf("Found at %d", i);
            return 0;
        }
    }
    return 0;
}
                """.trimIndent(),
                "Found at 2"
            ),

            PracticeTopicModel(
                "Binary Search",
                R.drawable.ic_search,
                "Binary Search",
                """
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
                """.trimIndent(),
                "Element found at position 4"
            ),

            PracticeTopicModel(
                "Trees",
                R.drawable.ic_tree,
                "Tree concept",
                """
#include <stdio.h>
int main(){
    printf("Root -> Left -> Right");
    return 0;
}
                """.trimIndent(),
                "Root -> Left -> Right"
            ),

            PracticeTopicModel(
                "Graphs",
                R.drawable.ic_graph,
                "Graph concept",
                """
#include <stdio.h>
int main(){
    printf("Graph = Nodes + Edges");
    return 0;
}
                """.trimIndent(),
                "Graph = Nodes + Edges"
            ),

            PracticeTopicModel(
                "Dynamic Programming",
                R.drawable.ic_dp,
                "Fibonacci DP",
                """
#include <stdio.h>
int main(){
    int dp[5]={0,1};
    for(int i=2;i<5;i++)
        dp[i]=dp[i-1]+dp[i-2];
    for(int i=0;i<5;i++) printf("%d ",dp[i]);
    return 0;
}
                """.trimIndent(),
                "0 1 1 2 3"
            )
        )

        val topicFromLearn = intent.getStringExtra("TOPIC")
        val subtypeFromLearn = intent.getStringExtra("SUBTYPE")

        if (!subtypeFromLearn.isNullOrEmpty()) {
            val selectedItem = list.find { it.title.equals(subtypeFromLearn, ignoreCase = true) }
            if (selectedItem != null) {
                openPracticeDetail(selectedItem)
                return
            }
        }

        if (!topicFromLearn.isNullOrEmpty()) {
            val selectedItem = list.find { it.title.equals(topicFromLearn, ignoreCase = true) }
            if (selectedItem != null) {
                openPracticeDetail(selectedItem)
                return
            }
        }

        recycler.adapter = PracticeTopicAdapter(list) { item ->
            openPracticeDetail(item)
        }

        btnOnlineCompiler.setOnClickListener {
            startActivity(Intent(this, CompilerActivity::class.java))
        }

        backBtn.setOnClickListener {
            finish()
        }
    }

    private fun openPracticeDetail(item: PracticeTopicModel) {
        val intent = Intent(this, PracticeDetailActivity::class.java)
        intent.putExtra("TITLE", item.title)
        intent.putExtra("CODE", item.code)
        intent.putExtra("OUTPUT", item.output)
        startActivity(intent)

    }
}