package com.rundsa.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rundsa.app.R
import com.rundsa.app.models.QuizQuestionModel

class QuizQuestionActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var questionCount: TextView
    private lateinit var timerText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var radioGroup: RadioGroup
    private lateinit var option1: RadioButton
    private lateinit var option2: RadioButton
    private lateinit var option3: RadioButton
    private lateinit var option4: RadioButton
    private lateinit var nextBtn: Button
    private lateinit var resultText: TextView

    private var currentIndex = 0
    private var score = 0
    private var questionList = listOf<QuizQuestionModel>()
    private var countDownTimer: CountDownTimer? = null
    private val totalTime = 30000L
    private var isAnswered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        val topicName = intent.getStringExtra("TOPIC") ?: ""
        val level = intent.getStringExtra("LEVEL") ?: "Easy"

        questionText = findViewById(R.id.questionText)
        questionCount = findViewById(R.id.questionCount)
        timerText = findViewById(R.id.timerText)
        progressBar = findViewById(R.id.progressBar)
        radioGroup = findViewById(R.id.radioGroup)
        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)
        nextBtn = findViewById(R.id.nextBtn)
        resultText = findViewById(R.id.resultText)

        questionList = getQuestions(topicName, level)

        if (questionList.isEmpty()) {
            Toast.makeText(this, "No questions found for topic: $topicName", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        progressBar.max = questionList.size
        showQuestion()

        nextBtn.setOnClickListener {
            if (!isAnswered) {
                checkAnswerAndMove()
            }
        }
    }

    private fun showQuestion() {
        if (currentIndex >= questionList.size) {
            openResultScreen()
            return
        }

        val question = questionList[currentIndex]
        questionCount.text = "Question ${currentIndex + 1}/${questionList.size}"
        progressBar.progress = currentIndex + 1
        questionText.text = question.question
        option1.text = question.options[0]
        option2.text = question.options[1]
        option3.text = question.options[2]
        option4.text = question.options[3]

        radioGroup.clearCheck()
        resultText.text = ""
        isAnswered = false
        nextBtn.text = "Next Question"

        enableOptions(true)
        startTimer()
    }

    private fun startTimer() {
        countDownTimer?.cancel()

        countDownTimer = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                timerText.text = "⏳ Time Left: ${seconds}s"
            }

            override fun onFinish() {
                timerText.text = "⏳ Time Left: 0s"
                resultText.text = "❌ Wrong! Time's up"
                resultText.setTextColor(resources.getColor(android.R.color.holo_red_light, theme))
                enableOptions(false)
                isAnswered = true

                Handler(Looper.getMainLooper()).postDelayed({
                    currentIndex++
                    showQuestion()
                }, 1500)
            }
        }.start()
    }

    private fun checkAnswerAndMove() {
        val selectedId = radioGroup.checkedRadioButtonId

        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
            return
        }

        val selectedOption = findViewById<RadioButton>(selectedId).text.toString()
        val correctAnswer = questionList[currentIndex].correctAnswer

        countDownTimer?.cancel()
        isAnswered = true
        enableOptions(false)

        if (selectedOption == correctAnswer) {
            score++
            resultText.text = "✅ Right"
            resultText.setTextColor(resources.getColor(android.R.color.holo_green_light, theme))
        } else {
            resultText.text = "❌ Wrong"
            resultText.setTextColor(resources.getColor(android.R.color.holo_red_light, theme))
        }

        Handler(Looper.getMainLooper()).postDelayed({
            currentIndex++
            showQuestion()
        }, 1500)
    }

    private fun enableOptions(enable: Boolean) {
        option1.isEnabled = enable
        option2.isEnabled = enable
        option3.isEnabled = enable
        option4.isEnabled = enable
    }

    private fun openResultScreen() {
        val resultIntent = Intent(this, QuizResultActivity::class.java)
        val topicName = intent.getStringExtra("TOPIC") ?: "Arrays"
        val level = intent.getStringExtra("LEVEL") ?: "Easy"

        resultIntent.putExtra("SCORE", score)
        resultIntent.putExtra("TOTAL", questionList.size)
        resultIntent.putExtra("TOPIC", topicName)
        resultIntent.putExtra("LEVEL", level)
        startActivity(resultIntent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

    private fun getQuestions(topic: String, level: String): List<QuizQuestionModel> {
        val cleanTopic = topic.trim()

        return when (cleanTopic) {
            "Arrays" -> getArrayQuestions(level)
            "Linked List" -> getLinkedListQuestions(level)
            "Stack" -> getStackQuestions(level)
            "Queue" -> getQueueQuestions(level)
            "Recursion" -> getRecursionQuestions(level)
            "Sorting" -> getSortingQuestions(level)
            "Searching" -> getSearchingQuestions(level)
            "Trees" -> getTreeQuestions(level)
            "Graphs" -> getGraphQuestions(level)
            "Dynamic Programming" -> getDynamicProgrammingQuestions(level)
            else -> emptyList()
        }
    }

    //Array
    private fun getArrayQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("What is an array?", listOf("Collection of elements", "Loop", "Function", "Class"), "Collection of elements"),
                QuizQuestionModel("Array index starts from?", listOf("0", "1", "-1", "2"), "0"),
                QuizQuestionModel("Which stores same type elements?", listOf("Array", "String", "Class", "Object"), "Array"),
                QuizQuestionModel("Which bracket is used in arrays?", listOf("[]", "()", "{}", "<>"), "[]"),
                QuizQuestionModel("Array size is usually?", listOf("Fixed", "Infinite", "Unknown", "Random"), "Fixed"),
                QuizQuestionModel("Which is valid array?", listOf("int[] a", "int a()", "array int", "a<int>"), "int[] a"),
                QuizQuestionModel("Array stores data in?", listOf("Sequence", "Tree", "Graph", "Stack only"), "Sequence"),
                QuizQuestionModel("Can array hold duplicate values?", listOf("Yes", "No", "Only strings", "Only int"), "Yes"),
                QuizQuestionModel("Access array element by?", listOf("Index", "Loop only", "Class", "Object"), "Index"),
                QuizQuestionModel("Array is useful for?", listOf("Storing multiple values", "Drawing", "Networking", "Login"), "Storing multiple values")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Time complexity to access array element?", listOf("O(1)", "O(n)", "O(log n)", "O(n2)"), "O(1)"),
                QuizQuestionModel("Insertion in middle of array is?", listOf("Costly", "Instant", "Impossible", "O(1) always"), "Costly"),
                QuizQuestionModel("Deletion in array may require?", listOf("Shifting", "Sorting", "Recursion", "Hashing"), "Shifting"),
                QuizQuestionModel("Binary search requires?", listOf("Sorted array", "Unsorted array", "Stack", "Queue"), "Sorted array"),
                QuizQuestionModel("Last index of size n array?", listOf("n-1", "n", "1", "0"), "n-1"),
                QuizQuestionModel("Traversal of array means?", listOf("Visit each element", "Delete all", "Reverse only", "Sort only"), "Visit each element"),
                QuizQuestionModel("2D array represents?", listOf("Rows and columns", "Only rows", "Only stack", "Only list"), "Rows and columns"),
                QuizQuestionModel("Which search works on unsorted array?", listOf("Linear search", "Binary search", "DFS", "BFS"), "Linear search"),
                QuizQuestionModel("Updating array element takes?", listOf("O(1)", "O(n)", "O(log n)", "O(n log n)"), "O(1)"),
                QuizQuestionModel("Array memory is?", listOf("Contiguous", "Random", "Linked", "Distributed"), "Contiguous")
            )
            else -> listOf(
                QuizQuestionModel("Best use of prefix sum in arrays?", listOf("Range sum queries", "Stack push", "Queue pop", "Tree height"), "Range sum queries"),
                QuizQuestionModel("Kadane's algorithm finds?", listOf("Maximum subarray sum", "Minimum value", "Sorted array", "Unique elements"), "Maximum subarray sum"),
                QuizQuestionModel("Two pointer technique is often used in?", listOf("Arrays", "Trees only", "Graphs only", "DP only"), "Arrays"),
                QuizQuestionModel("Sliding window is useful for?", listOf("Subarray problems", "Graph edges", "Tree roots", "Stack recursion"), "Subarray problems"),
                QuizQuestionModel("Array rotation by k means?", listOf("Shift elements", "Sort elements", "Delete elements", "Merge arrays"), "Shift elements"),
                QuizQuestionModel("Frequency counting in arrays often uses?", listOf("Hash map", "Queue", "Tree root", "Recursion only"), "Hash map"),
                QuizQuestionModel("Merge two sorted arrays can be done in?", listOf("Linear time", "Quadratic time only", "Impossible", "Constant time"), "Linear time"),
                QuizQuestionModel("Maximum product subarray is a?", listOf("Array problem", "Graph problem", "OS problem", "DBMS problem"), "Array problem"),
                QuizQuestionModel("Dutch national flag problem is related to?", listOf("Sorting array of 0s 1s 2s", "Linked list reverse", "Graph coloring", "Queue rotation"), "Sorting array of 0s 1s 2s"),
                QuizQuestionModel("Majority element can be found by?", listOf("Boyer-Moore Voting", "BFS", "DFS", "Dijkstra"), "Boyer-Moore Voting")
            )
        }
    }
    //linkedlist
    private fun getLinkedListQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("What is a linked list?", listOf("Collection of nodes", "Collection of arrays", "Tree", "Graph"), "Collection of nodes"),
                QuizQuestionModel("Each node in a singly linked list contains?", listOf("Data and next pointer", "Only data", "Only pointer", "Index and data"), "Data and next pointer"),
                QuizQuestionModel("First node of linked list is called?", listOf("Head", "Tail", "Root", "Top"), "Head"),
                QuizQuestionModel("Last node points to?", listOf("Null", "Head", "Root", "Previous node"), "Null"),
                QuizQuestionModel("Linked list elements are stored in?", listOf("Non-contiguous memory", "Contiguous memory", "Stack memory only", "Cache only"), "Non-contiguous memory"),
                QuizQuestionModel("Which is easier in linked list than array?", listOf("Insertion", "Random access", "Binary search", "Indexing"), "Insertion"),
                QuizQuestionModel("Last node is called?", listOf("Tail", "Head", "Top", "Root"), "Tail"),
                QuizQuestionModel("A doubly linked list has?", listOf("Prev and next pointers", "Only next pointer", "Only prev pointer", "No pointer"), "Prev and next pointers"),
                QuizQuestionModel("Circular linked list last node points to?", listOf("Head", "Null", "Tail", "Middle"), "Head"),
                QuizQuestionModel("Linked list is useful for?", listOf("Dynamic size data", "Fixed size only", "Sorting only", "Math only"), "Dynamic size data")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Time complexity to insert at beginning of linked list?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("Time complexity to search an element in linked list?", listOf("O(n)", "O(1)", "O(log n)", "O(n log n)"), "O(n)"),
                QuizQuestionModel("Which traversal is possible in singly linked list?", listOf("Forward only", "Backward only", "Both directions", "Random"), "Forward only"),
                QuizQuestionModel("Deletion at beginning of linked list takes?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("Which pointer is used to move to next node?", listOf("next", "prev", "head", "top"), "next"),
                QuizQuestionModel("To insert at end in singly linked list, we usually need?", listOf("Traversal", "Binary search", "Recursion only", "Stack"), "Traversal"),
                QuizQuestionModel("Doubly linked list uses extra memory for?", listOf("Previous pointer", "Array index", "Hash code", "Queue"), "Previous pointer"),
                QuizQuestionModel("Circular linked list has?", listOf("No null at end", "Two heads", "No data", "Only one node always"), "No null at end"),
                QuizQuestionModel("Random access in linked list is?", listOf("Slow", "Fast", "Constant always", "Impossible"), "Slow"),
                QuizQuestionModel("Middle insertion in linked list after reaching node is?", listOf("Easy", "Hard", "Impossible", "Always O(n²)"), "Easy")
            )
            else -> listOf(
                QuizQuestionModel("Which algorithm detects loop in linked list?", listOf("Floyd cycle detection", "Dijkstra", "Merge sort", "Kadane"), "Floyd cycle detection"),
                QuizQuestionModel("Reversing a linked list mainly changes?", listOf("Pointers", "Node data type", "Array size", "Graph edges"), "Pointers"),
                QuizQuestionModel("Fast and slow pointers are used to find?", listOf("Middle node", "Minimum value", "Array size", "Root node"), "Middle node"),
                QuizQuestionModel("Merging two sorted linked lists can be done in?", listOf("Linear time", "Constant time", "Quadratic time only", "Impossible"), "Linear time"),
                QuizQuestionModel("Which linked list is best for back and forward navigation?", listOf("Doubly linked list", "Singly linked list", "Circular queue", "Array"), "Doubly linked list"),
                QuizQuestionModel("Removing duplicates from sorted linked list usually needs?", listOf("Traversal", "Heap", "BFS", "Binary search"), "Traversal"),
                QuizQuestionModel("Intersection of two linked lists can be solved using?", listOf("Two pointers", "Only recursion", "Only stack", "Dijkstra"), "Two pointers"),
                QuizQuestionModel("Palindrome linked list often uses?", listOf("Reverse second half", "Sorting", "Binary tree", "Hash table only"), "Reverse second half"),
                QuizQuestionModel("Cycle in linked list means?", listOf("Node points back to previous nodes", "Data repeats", "List is sorted", "Two heads exist"), "Node points back to previous nodes"),
                QuizQuestionModel("LRU cache commonly uses?", listOf("HashMap + Doubly Linked List", "Only Array", "Only Queue", "Only Tree"), "HashMap + Doubly Linked List")
            )
        }
    }
//Stack
    private fun getStackQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Stack follows which principle?", listOf("LIFO", "FIFO", "LILO", "FILO only"), "LIFO"),
                QuizQuestionModel("Top operation returns?", listOf("Top element", "Bottom element", "Size", "Null always"), "Top element"),
                QuizQuestionModel("Insert into stack is called?", listOf("Push", "Pop", "Peek", "Insert"), "Push"),
                QuizQuestionModel("Delete from stack is called?", listOf("Pop", "Push", "Peek", "RemoveLast"), "Pop"),
                QuizQuestionModel("View top element without deleting is?", listOf("Peek", "Pop", "Push", "TopDelete"), "Peek"),
                QuizQuestionModel("Which end is used in stack?", listOf("One end only", "Both ends", "Middle", "Random"), "One end only"),
                QuizQuestionModel("A stack can be implemented using?", listOf("Array or linked list", "Only tree", "Only graph", "Only queue"), "Array or linked list"),
                QuizQuestionModel("Browser back button uses?", listOf("Stack", "Queue", "Tree", "Graph"), "Stack"),
                QuizQuestionModel("If stack is empty and pop is called, it is?", listOf("Underflow", "Overflow", "Traversal", "Recursion"), "Underflow"),
                QuizQuestionModel("If stack is full and push is called, it is?", listOf("Overflow", "Underflow", "Loop", "Collision"), "Overflow")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Push operation time complexity is?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("Pop operation time complexity is?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("Which data structure is used in function calls?", listOf("Stack", "Queue", "Graph", "Heap"), "Stack"),
                QuizQuestionModel("Balanced parentheses problem uses?", listOf("Stack", "Queue", "Array only", "Tree"), "Stack"),
                QuizQuestionModel("Postfix evaluation commonly uses?", listOf("Stack", "Queue", "HashMap", "Graph"), "Stack"),
                QuizQuestionModel("Undo operation in editor often uses?", listOf("Stack", "Queue", "Heap", "Tree"), "Stack"),
                QuizQuestionModel("Multiple pops from empty stack cause?", listOf("Underflow", "Overflow", "Segmentation sort", "Traversal"), "Underflow"),
                QuizQuestionModel("In array implementation of stack, top indicates?", listOf("Current top index", "Bottom index", "Maximum value", "Null"), "Current top index"),
                QuizQuestionModel("Recursion internally uses?", listOf("Call stack", "Queue", "Heap only", "Tree"), "Call stack"),
                QuizQuestionModel("Which notation is easiest to evaluate using stack?", listOf("Postfix", "Infix only", "Prefix only impossible", "Binary"), "Postfix")
            )
            else -> listOf(
                QuizQuestionModel("Infix to postfix conversion uses?", listOf("Stack", "Queue", "Graph", "BST"), "Stack"),
                QuizQuestionModel("Monotonic stack is used for?", listOf("Next greater/smaller element", "BFS", "Shortest path", "Tree balancing"), "Next greater/smaller element"),
                QuizQuestionModel("Largest rectangle in histogram uses?", listOf("Stack", "Queue", "Trie", "Heap"), "Stack"),
                QuizQuestionModel("Minimum stack supports getMin in?", listOf("O(1)", "O(n)", "O(log n)", "O(n log n)"), "O(1)"),
                QuizQuestionModel("Stack overflow may happen due to?", listOf("Deep recursion", "Queue deletion", "Sorting only", "Hash collision"), "Deep recursion"),
                QuizQuestionModel("To reverse a string, suitable structure is?", listOf("Stack", "Queue", "Tree", "Graph"), "Stack"),
                QuizQuestionModel("Expression parsing in compilers often uses?", listOf("Stack", "Queue only", "Linked list only", "Array only"), "Stack"),
                QuizQuestionModel("Stock span problem is solved using?", listOf("Stack", "DFS", "Heap sort", "Union find"), "Stack"),
                QuizQuestionModel("Stack with two queues is an example of?", listOf("Implementation technique", "Graph cycle", "Tree traversal", "DP"), "Implementation technique"),
                QuizQuestionModel("To check valid parentheses in O(n), use?", listOf("Stack", "Queue", "Binary Search", "Trie"), "Stack")
            )
        }
    }
    //Queue
    private fun getQueueQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Queue follows which principle?", listOf("FIFO", "LIFO", "LILO", "FILO"), "FIFO"),
                QuizQuestionModel("Insertion in queue is called?", listOf("Enqueue", "Push", "Pop", "Peek"), "Enqueue"),
                QuizQuestionModel("Deletion from queue is called?", listOf("Dequeue", "Push", "Pop", "Insert"), "Dequeue"),
                QuizQuestionModel("Element removed from queue comes from?", listOf("Front", "Rear", "Middle", "Anywhere"), "Front"),
                QuizQuestionModel("New element is added at?", listOf("Rear", "Front", "Middle", "Top"), "Rear"),
                QuizQuestionModel("Which structure is used in ticket line?", listOf("Queue", "Stack", "Tree", "Graph"), "Queue"),
                QuizQuestionModel("Queue can be implemented using?", listOf("Array or linked list", "Only tree", "Only graph", "Only stack"), "Array or linked list"),
                QuizQuestionModel("Front operation returns?", listOf("First element", "Last element", "Middle element", "Null always"), "First element"),
                QuizQuestionModel("Circular queue helps to?", listOf("Reuse empty space", "Sort faster", "Search faster", "Use recursion"), "Reuse empty space"),
                QuizQuestionModel("Printer scheduling often uses?", listOf("Queue", "Stack", "Tree", "Heap"), "Queue")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Enqueue operation time complexity is?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("Dequeue operation time complexity is?", listOf("O(1)", "O(n)", "O(log n)", "O(n²)"), "O(1)"),
                QuizQuestionModel("BFS traversal uses?", listOf("Queue", "Stack", "Heap", "Trie"), "Queue"),
                QuizQuestionModel("Queue underflow occurs when?", listOf("Deleting from empty queue", "Adding to full queue", "Sorting queue", "Traversing queue"), "Deleting from empty queue"),
                QuizQuestionModel("Queue overflow occurs when?", listOf("Adding to full queue", "Deleting from empty queue", "Reversing queue", "Searching queue"), "Adding to full queue"),
                QuizQuestionModel("Circular queue rear moves using?", listOf("Modulo operation", "Recursion", "Heapify", "Hashing"), "Modulo operation"),
                QuizQuestionModel("Deque allows insertion/deletion at?", listOf("Both ends", "Front only", "Rear only", "Middle only"), "Both ends"),
                QuizQuestionModel("Priority queue removes?", listOf("Highest/lowest priority element", "Front inserted element always", "Last inserted element always", "Random element"), "Highest/lowest priority element"),
                QuizQuestionModel("Simple queue is best for?", listOf("Order processing", "Undo operation", "Balanced parentheses", "Recursion stack"), "Order processing"),
                QuizQuestionModel("Front and rear are used to track?", listOf("Queue ends", "Tree roots", "Graph edges", "Stack tops"), "Queue ends")
            )
            else -> listOf(
                QuizQuestionModel("Which traversal in tree uses queue?", listOf("Level order traversal", "Inorder", "Postorder", "Reverse recursion"), "Level order traversal"),
                QuizQuestionModel("Sliding window maximum can be solved using?", listOf("Deque", "Stack only", "Tree only", "Array only"), "Deque"),
                QuizQuestionModel("Implementing stack using queues is?", listOf("Possible", "Impossible", "Only with recursion", "Only with tree"), "Possible"),
                QuizQuestionModel("Priority queue is often implemented using?", listOf("Heap", "Linked list only", "Array only", "Graph"), "Heap"),
                QuizQuestionModel("Round-robin scheduling uses?", listOf("Queue", "Stack", "Trie", "BST"), "Queue"),
                QuizQuestionModel("Shortest path in unweighted graph uses?", listOf("Queue with BFS", "Stack with DFS", "Heap sort", "DP only"), "Queue with BFS"),
                QuizQuestionModel("Double ended queue is called?", listOf("Deque", "Stack", "Heap", "Trie"), "Deque"),
                QuizQuestionModel("Queue with recursion can be reversed using?", listOf("Call stack", "Binary search", "Tree rotation", "Hashing"), "Call stack"),
                QuizQuestionModel("Queue is best when data should be processed in?", listOf("Arrival order", "Reverse order", "Sorted order", "Random order"), "Arrival order"),
                QuizQuestionModel("Message buffering systems often use?", listOf("Queue", "Stack", "Tree", "Graph"), "Queue")
            )
        }
    }
    //Recursion
    private fun getRecursionQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("What is recursion?", listOf("Function calling itself", "Loop inside array", "Class inheritance", "Sorting method"), "Function calling itself"),
                QuizQuestionModel("Recursion must have?", listOf("Base case", "Queue", "Pointer", "Graph"), "Base case"),
                QuizQuestionModel("Without base case recursion may cause?", listOf("Infinite calls", "Fast execution", "Sorting", "Compilation success only"), "Infinite calls"),
                QuizQuestionModel("Recursion uses?", listOf("Call stack", "Queue", "Heap", "HashMap"), "Call stack"),
                QuizQuestionModel("Factorial can be solved using?", listOf("Recursion", "Only graph", "Only queue", "Only heap"), "Recursion"),
                QuizQuestionModel("Base case stops?", listOf("Recursive calls", "Compilation", "Variables", "Arrays"), "Recursive calls"),
                QuizQuestionModel("Recursive function breaks problem into?", listOf("Smaller subproblems", "Bigger arrays", "Trees only", "Graphs only"), "Smaller subproblems"),
                QuizQuestionModel("Which is common recursion example?", listOf("Fibonacci", "Queue dequeue", "Hash collision", "Bubble memory"), "Fibonacci"),
                QuizQuestionModel("Recursion is similar to?", listOf("Repeated self-calls", "Random jumps", "Static memory", "Indexing only"), "Repeated self-calls"),
                QuizQuestionModel("Each recursive call gets?", listOf("Its own stack frame", "Same stack frame", "Queue node", "Heap root"), "Its own stack frame")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Time complexity of naive Fibonacci recursion is?", listOf("Exponential", "Constant", "Linear", "Logarithmic"), "Exponential"),
                QuizQuestionModel("Recursion can often be replaced with?", listOf("Iteration", "Heap only", "Hashing", "Sorting"), "Iteration"),
                QuizQuestionModel("Which traversal of tree commonly uses recursion?", listOf("DFS traversals", "BFS only", "Priority queue", "Hash table"), "DFS traversals"),
                QuizQuestionModel("Tail recursion means?", listOf("Recursive call is last operation", "First operation is recursive", "No base case", "Two recursive calls"), "Recursive call is last operation"),
                QuizQuestionModel("Stack overflow in recursion happens due to?", listOf("Too many calls", "Too many queues", "Too many arrays", "Too many graphs"), "Too many calls"),
                QuizQuestionModel("Divide and conquer algorithms often use?", listOf("Recursion", "Only loops", "Only stacks", "Only queues"), "Recursion"),
                QuizQuestionModel("Which problem naturally uses recursion?", listOf("Tower of Hanoi", "Linear queue", "Circular array", "Heapify only"), "Tower of Hanoi"),
                QuizQuestionModel("Recursive binary search has time complexity?", listOf("O(log n)", "O(n)", "O(1)", "O(n²)"), "O(log n)"),
                QuizQuestionModel("Backtracking is related to?", listOf("Recursion", "Queue only", "Hashing only", "Heap only"), "Recursion"),
                QuizQuestionModel("In recursion, each call waits for?", listOf("Returned result", "Queue front", "Heap root", "Graph edge"), "Returned result")
            )
            else -> listOf(
                QuizQuestionModel("Memoization improves recursion by?", listOf("Storing repeated results", "Increasing stack size", "Using queue", "Using graph"), "Storing repeated results"),
                QuizQuestionModel("Which is a classic backtracking problem?", listOf("N-Queens", "Bubble sort", "BFS queue", "Circular array"), "N-Queens"),
                QuizQuestionModel("Merge sort uses recursion with time complexity?", listOf("O(n log n)", "O(n²)", "O(log n)", "O(1)"), "O(n log n)"),
                QuizQuestionModel("Quick sort average complexity is?", listOf("O(n log n)", "O(n²) always", "O(1)", "O(log n)"), "O(n log n)"),
                QuizQuestionModel("Recursive DFS uses?", listOf("Call stack", "Queue", "Heap", "Deque"), "Call stack"),
                QuizQuestionModel("Backtracking differs from recursion because it?", listOf("Explores and undoes choices", "Never returns", "Uses queue only", "Uses heap only"), "Explores and undoes choices"),
                QuizQuestionModel("Subset generation problems often use?", listOf("Recursion/backtracking", "Heap sort", "Union find", "Trie"), "Recursion/backtracking"),
                QuizQuestionModel("Recursion tree is used to analyze?", listOf("Time complexity", "Only memory address", "Only queue size", "Only graph degree"), "Time complexity"),
                QuizQuestionModel("Tail recursive functions may be optimized by?", listOf("Compiler", "Queue", "HashMap", "Graph"), "Compiler"),
                QuizQuestionModel("Dynamic programming top-down approach uses?", listOf("Recursion + memoization", "Queue + BFS", "Stack only", "Greedy only"), "Recursion + memoization")
            )
        }
    }
    //Sorting
    private fun getSortingQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Sorting means?", listOf("Arranging data in order", "Deleting data", "Searching data", "Reversing only"), "Arranging data in order"),
                QuizQuestionModel("Bubble sort compares?", listOf("Adjacent elements", "Random elements", "Only first and last", "Only middle"), "Adjacent elements"),
                QuizQuestionModel("Sorted order can be?", listOf("Ascending or descending", "Only ascending", "Only descending", "Only random"), "Ascending or descending"),
                QuizQuestionModel("Which sort repeatedly selects smallest element?", listOf("Selection sort", "Bubble sort", "Quick sort", "Heap sort"), "Selection sort"),
                QuizQuestionModel("Which sort inserts element into correct position?", listOf("Insertion sort", "Merge sort", "Counting sort", "DFS sort"), "Insertion sort"),
                QuizQuestionModel("Sorting is useful for?", listOf("Easy searching and organization", "Only recursion", "Only graphs", "Only stacks"), "Easy searching and organization"),
                QuizQuestionModel("Bubble sort swaps when?", listOf("Left element is greater", "Elements are equal only", "Right is smaller only impossible", "Array is sorted"), "Left element is greater"),
                QuizQuestionModel("Selection sort places correct element at?", listOf("Beginning of unsorted part", "End only", "Middle", "Random"), "Beginning of unsorted part"),
                QuizQuestionModel("Insertion sort works well for?", listOf("Small or nearly sorted arrays", "Huge random graphs", "Heap trees only", "Queues only"), "Small or nearly sorted arrays"),
                QuizQuestionModel("Sorting algorithms work on?", listOf("Data collections", "Only strings", "Only graphs", "Only integers"), "Data collections")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Bubble sort average time complexity is?", listOf("O(n²)", "O(n)", "O(log n)", "O(n log n)"), "O(n²)"),
                QuizQuestionModel("Selection sort average time complexity is?", listOf("O(n²)", "O(n)", "O(log n)", "O(1)"), "O(n²)"),
                QuizQuestionModel("Insertion sort best case is?", listOf("O(n)", "O(n²)", "O(log n)", "O(1)"), "O(n)"),
                QuizQuestionModel("Merge sort time complexity is?", listOf("O(n log n)", "O(n²)", "O(n)", "O(log n)"), "O(n log n)"),
                QuizQuestionModel("Quick sort average time complexity is?", listOf("O(n log n)", "O(n²)", "O(n)", "O(1)"), "O(n log n)"),
                QuizQuestionModel("Stable sort keeps?", listOf("Relative order of equal elements", "Largest element first", "Array size fixed", "Pivot fixed"), "Relative order of equal elements"),
                QuizQuestionModel("In merge sort, array is divided into?", listOf("Two halves", "Three parts", "Four equal stacks", "Random graphs"), "Two halves"),
                QuizQuestionModel("Quick sort uses?", listOf("Pivot element", "Queue front", "Tree root only", "Hash key"), "Pivot element"),
                QuizQuestionModel("Heap sort is based on?", listOf("Heap", "Queue", "Linked list", "Trie"), "Heap"),
                QuizQuestionModel("Which sorting algorithm is in-place?", listOf("Quick sort", "Merge sort always", "Counting sort always", "Radix sort always"), "Quick sort")
            )
            else -> listOf(
                QuizQuestionModel("Worst-case time complexity of quick sort is?", listOf("O(n²)", "O(n log n)", "O(n)", "O(log n)"), "O(n²)"),
                QuizQuestionModel("Merge sort extra space complexity is?", listOf("O(n)", "O(1)", "O(log n)", "O(n²)"), "O(n)"),
                QuizQuestionModel("Counting sort works best when?", listOf("Range of values is small", "Data is huge random strings", "Graph is weighted", "Tree is balanced"), "Range of values is small"),
                QuizQuestionModel("Radix sort sorts numbers by?", listOf("Digits", "Random pivot", "Heap root", "Graph depth"), "Digits"),
                QuizQuestionModel("Which sort is not comparison-based?", listOf("Counting sort", "Merge sort", "Quick sort", "Heap sort"), "Counting sort"),
                QuizQuestionModel("Heap sort always has complexity?", listOf("O(n log n)", "O(n²)", "O(log n)", "O(1)"), "O(n log n)"),
                QuizQuestionModel("Tim sort is used in many libraries because it is?", listOf("Efficient on real-world data", "Only for graphs", "Only recursive", "Only unstable"), "Efficient on real-world data"),
                QuizQuestionModel("External sorting is used when?", listOf("Data doesn't fit in memory", "Array is tiny", "Graph has cycle", "Tree is complete"), "Data doesn't fit in memory"),
                QuizQuestionModel("Choosing median pivot in quick sort helps?", listOf("Reduce bad partitions", "Increase memory always", "Make it BFS", "Make it stable"), "Reduce bad partitions"),
                QuizQuestionModel("Which sort is naturally stable?", listOf("Merge sort", "Heap sort", "Quick sort", "Selection sort"), "Merge sort")
            )
        }
    }
//Searching
    private fun getSearchingQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Searching means?", listOf("Finding an element", "Sorting elements", "Deleting elements", "Reversing elements"), "Finding an element"),
                QuizQuestionModel("Linear search checks?", listOf("Each element one by one", "Middle only", "First only", "Last only"), "Each element one by one"),
                QuizQuestionModel("Binary search works on?", listOf("Sorted array", "Unsorted array", "Graph only", "Queue only"), "Sorted array"),
                QuizQuestionModel("If target is found, searching is?", listOf("Successful", "Failed", "Sorted", "Reversed"), "Successful"),
                QuizQuestionModel("Linear search can work on?", listOf("Any list", "Only sorted list", "Only tree", "Only heap"), "Any list"),
                QuizQuestionModel("Binary search checks which element first?", listOf("Middle element", "First element", "Last element", "Random element"), "Middle element"),
                QuizQuestionModel("Searching is useful for?", listOf("Locating required data", "Only sorting", "Only deletion", "Only graphs"), "Locating required data"),
                QuizQuestionModel("Linear search is simple but?", listOf("Slow for large data", "Impossible to code", "Needs recursion always", "Only works on numbers"), "Slow for large data"),
                QuizQuestionModel("Binary search repeatedly divides data into?", listOf("Two halves", "Three parts", "Four heaps", "Random nodes"), "Two halves"),
                QuizQuestionModel("Which search is better for sorted large array?", listOf("Binary search", "Linear search", "DFS", "BFS"), "Binary search")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Linear search time complexity is?", listOf("O(n)", "O(1)", "O(log n)", "O(n log n)"), "O(n)"),
                QuizQuestionModel("Binary search time complexity is?", listOf("O(log n)", "O(n)", "O(1)", "O(n²)"), "O(log n)"),
                QuizQuestionModel("Binary search best case is?", listOf("O(1)", "O(n)", "O(log n)", "O(n log n)"), "O(1)"),
                QuizQuestionModel("Which condition is required for binary search?", listOf("Data must be sorted", "Data must be reversed", "Data must be unique", "Data must be graph"), "Data must be sorted"),
                QuizQuestionModel("If target is greater than mid in binary search, go to?", listOf("Right half", "Left half", "Both halves", "Restart"), "Right half"),
                QuizQuestionModel("If target is smaller than mid, go to?", listOf("Left half", "Right half", "Both halves", "Queue"), "Left half"),
                QuizQuestionModel("Binary search can be implemented using?", listOf("Iteration or recursion", "Only graph", "Only queue", "Only stack"), "Iteration or recursion"),
                QuizQuestionModel("Searching in linked list is usually?", listOf("Linear", "Binary", "Constant", "Heap"), "Linear"),
                QuizQuestionModel("Which search does not need sorted data?", listOf("Linear search", "Binary search", "Interpolation search", "Ternary search"), "Linear search"),
                QuizQuestionModel("Mid index in binary search is used to?", listOf("Compare with target", "Swap values", "Delete element", "Sort array"), "Compare with target")
            )
            else -> listOf(
                QuizQuestionModel("Interpolation search works best on?", listOf("Uniformly distributed sorted data", "Random graph", "Stack", "Queue"), "Uniformly distributed sorted data"),
                QuizQuestionModel("Ternary search divides array into?", listOf("Three parts", "Two parts", "Four parts", "Random parts"), "Three parts"),
                QuizQuestionModel("Lower bound in searching finds?", listOf("First position not less than target", "Last position", "Middle element", "Minimum value"), "First position not less than target"),
                QuizQuestionModel("Upper bound finds?", listOf("First position greater than target", "Last smaller element", "Exact target only", "Minimum"), "First position greater than target"),
                QuizQuestionModel("Finding first and last occurrence in sorted array often uses?", listOf("Modified binary search", "DFS", "Heap", "Queue"), "Modified binary search"),
                QuizQuestionModel("Exponential search is useful when?", listOf("Range is unbounded or unknown", "Graph has cycle", "Stack is full", "Queue is circular"), "Range is unbounded or unknown"),
                QuizQuestionModel("Search in rotated sorted array is usually solved using?", listOf("Binary search logic", "Linear only", "Heap sort", "BFS"), "Binary search logic"),
                QuizQuestionModel("Peak element can often be found in?", listOf("O(log n)", "O(n²)", "O(n log n)", "O(1) always"), "O(log n)"),
                QuizQuestionModel("Searching in BST average time is?", listOf("O(log n)", "O(n²)", "O(1)", "O(n log n)"), "O(log n)"),
                QuizQuestionModel("Binary search on answer is used in?", listOf("Optimization problems", "Only graph coloring", "Only stack problems", "Only queues"), "Optimization problems")
            )
        }
    }
   //Graph
    private fun getGraphQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Graph consists of?", listOf("Vertices and edges", "Only nodes", "Only pointers", "Only arrays"), "Vertices and edges"),
                QuizQuestionModel("Another name for vertex is?", listOf("Node", "Root", "Leaf", "Top"), "Node"),
                QuizQuestionModel("Connection between two vertices is called?", listOf("Edge", "Pointer", "Queue", "Index"), "Edge"),
                QuizQuestionModel("Graph can be?", listOf("Directed or undirected", "Only directed", "Only undirected", "Only weighted"), "Directed or undirected"),
                QuizQuestionModel("Directed graph edge has?", listOf("Direction", "No vertices", "Two roots", "No weight"), "Direction"),
                QuizQuestionModel("Undirected graph edge has?", listOf("No direction", "Fixed direction", "Only one node", "No connection"), "No direction"),
                QuizQuestionModel("Weighted graph edges have?", listOf("Weights/costs", "Colors only", "Queues", "Stacks"), "Weights/costs"),
                QuizQuestionModel("Social network connections can be modeled by?", listOf("Graph", "Stack", "Queue", "Array only"), "Graph"),
                QuizQuestionModel("A graph may contain?", listOf("Cycles", "Only trees", "Only arrays", "Only one node"), "Cycles"),
                QuizQuestionModel("Graph is used to represent?", listOf("Relationships/networks", "Only numbers", "Only recursion", "Only arrays"), "Relationships/networks")
            )
            "Medium" -> listOf(
                QuizQuestionModel("BFS stands for?", listOf("Breadth First Search", "Binary First Search", "Best First Sort", "Breadth Fast Search"), "Breadth First Search"),
                QuizQuestionModel("DFS stands for?", listOf("Depth First Search", "Data First Search", "Dynamic Fast Search", "Depth Find Sort"), "Depth First Search"),
                QuizQuestionModel("BFS uses?", listOf("Queue", "Stack", "Heap", "Trie"), "Queue"),
                QuizQuestionModel("DFS uses?", listOf("Stack or recursion", "Queue only", "Heap only", "Array only"), "Stack or recursion"),
                QuizQuestionModel("Adjacency matrix uses space?", listOf("O(V²)", "O(V)", "O(E)", "O(log V)"), "O(V²)"),
                QuizQuestionModel("Adjacency list is better for?", listOf("Sparse graphs", "Dense graphs always", "Only weighted graphs", "Only trees"), "Sparse graphs"),
                QuizQuestionModel("BFS is useful for shortest path in?", listOf("Unweighted graph", "Weighted graph with negative edges", "Heap only", "Tree only"), "Unweighted graph"),
                QuizQuestionModel("DFS helps in?", listOf("Traversal and cycle detection", "Priority queue only", "Hashing only", "Sorting only"), "Traversal and cycle detection"),
                QuizQuestionModel("A cycle means?", listOf("Path returning to same vertex", "Disconnected graph", "Single node only", "No edge"), "Path returning to same vertex"),
                QuizQuestionModel("Connected graph means?", listOf("Every node reachable", "No edges", "Only one node", "Only weighted"), "Every node reachable")
            )
            else -> listOf(
                QuizQuestionModel("Dijkstra algorithm finds?", listOf("Shortest path with non-negative weights", "Minimum spanning tree only", "Topological order", "Cycle count"), "Shortest path with non-negative weights"),
                QuizQuestionModel("Topological sorting applies to?", listOf("DAG", "Undirected cyclic graph", "Heap", "Queue"), "DAG"),
                QuizQuestionModel("MST stands for?", listOf("Minimum Spanning Tree", "Maximum Search Tree", "Multi Source Traversal", "Minimum Sorted Trie"), "Minimum Spanning Tree"),
                QuizQuestionModel("Prim's and Kruskal's algorithms are used for?", listOf("Minimum Spanning Tree", "Shortest path only", "DFS only", "Topological sort only"), "Minimum Spanning Tree"),
                QuizQuestionModel("Bellman-Ford can handle?", listOf("Negative edge weights", "Only positive edges", "Only trees", "Only heaps"), "Negative edge weights"),
                QuizQuestionModel("Floyd-Warshall is used for?", listOf("All-pairs shortest path", "Single source DFS", "Topological sorting", "Connected components only"), "All-pairs shortest path"),
                QuizQuestionModel("Union-Find is useful for?", listOf("Cycle detection and components", "Queue management", "String matching", "Heap balancing"), "Cycle detection and components"),
                QuizQuestionModel("Bipartite graph can be checked using?", listOf("BFS/DFS coloring", "Heap sort", "Binary search", "Trie"), "BFS/DFS coloring"),
                QuizQuestionModel("Strongly connected components are in?", listOf("Directed graphs", "Only trees", "Only heaps", "Only arrays"), "Directed graphs"),
                QuizQuestionModel("Kosaraju/Tarjan algorithms are for?", listOf("Strongly connected components", "MST", "Sorting", "Searching arrays"), "Strongly connected components")
            )
        }
    }
    //Tree
    private fun getTreeQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Tree is a?", listOf("Hierarchical data structure", "Linear data structure", "Only sorting method", "Searching algorithm"), "Hierarchical data structure"),
                QuizQuestionModel("Top node of a tree is called?", listOf("Root", "Leaf", "Head", "Top"), "Root"),
                QuizQuestionModel("Node with no children is called?", listOf("Leaf", "Root", "Parent", "Edge"), "Leaf"),
                QuizQuestionModel("Connection between two nodes is called?", listOf("Edge", "Pointer", "Stack", "Index"), "Edge"),
                QuizQuestionModel("Binary tree node can have at most?", listOf("2 children", "1 child", "3 children", "4 children"), "2 children"),
                QuizQuestionModel("Children of same parent are called?", listOf("Siblings", "Roots", "Leaves", "Heaps"), "Siblings"),
                QuizQuestionModel("Parent of a node is the node?", listOf("Directly above it", "Directly below it", "At far left", "At far right"), "Directly above it"),
                QuizQuestionModel("Tree starts from?", listOf("Root", "Leaf", "Middle", "Queue"), "Root"),
                QuizQuestionModel("A subtree is?", listOf("Tree inside a tree", "Queue in tree", "Stack node", "Sorted array"), "Tree inside a tree"),
                QuizQuestionModel("Binary tree is used in?", listOf("Hierarchical representation", "Only queues", "Only sorting", "Only graphs"), "Hierarchical representation")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Inorder traversal of binary tree is?", listOf("Left Root Right", "Root Left Right", "Left Right Root", "Right Root Left"), "Left Root Right"),
                QuizQuestionModel("Preorder traversal is?", listOf("Root Left Right", "Left Root Right", "Left Right Root", "Right Left Root"), "Root Left Right"),
                QuizQuestionModel("Postorder traversal is?", listOf("Left Right Root", "Root Left Right", "Left Root Right", "Right Root Left"), "Left Right Root"),
                QuizQuestionModel("Level order traversal uses?", listOf("Queue", "Stack", "Heap", "Trie"), "Queue"),
                QuizQuestionModel("Height of tree means?", listOf("Longest path from root to leaf", "Number of roots", "Number of siblings", "Width only"), "Longest path from root to leaf"),
                QuizQuestionModel("BST stands for?", listOf("Binary Search Tree", "Balanced Sorted Tree", "Binary Stack Tree", "Big Search Tree"), "Binary Search Tree"),
                QuizQuestionModel("In BST, left subtree values are?", listOf("Less than root", "Greater than root", "Equal always", "Random"), "Less than root"),
                QuizQuestionModel("In BST, right subtree values are?", listOf("Greater than root", "Less than root", "Equal always", "Random"), "Greater than root"),
                QuizQuestionModel("Searching in balanced BST average time is?", listOf("O(log n)", "O(n)", "O(1)", "O(n²)"), "O(log n)"),
                QuizQuestionModel("DFS traversal often uses?", listOf("Recursion or stack", "Queue only", "Heap only", "Hashing"), "Recursion or stack")
            )
            else -> listOf(
                QuizQuestionModel("AVL tree is a?", listOf("Self-balancing BST", "Heap", "Trie", "Queue"), "Self-balancing BST"),
                QuizQuestionModel("Balance factor in AVL tree is based on?", listOf("Height difference", "Node value", "Queue size", "Heap depth"), "Height difference"),
                QuizQuestionModel("Heap is commonly used for?", listOf("Priority queue", "Balanced parentheses", "Undo operation", "String reverse"), "Priority queue"),
                QuizQuestionModel("Max heap property means?", listOf("Parent >= children", "Parent <= children", "Left always bigger", "Right always bigger"), "Parent >= children"),
                QuizQuestionModel("Min heap property means?", listOf("Parent <= children", "Parent >= children", "Leaves are sorted", "Root is leaf"), "Parent <= children"),
                QuizQuestionModel("Lowest Common Ancestor means?", listOf("Shared ancestor closest to nodes", "Root only", "Leaf only", "Parent of one node only"), "Shared ancestor closest to nodes"),
                QuizQuestionModel("Trie is mainly used for?", listOf("Strings/prefix search", "Heaps", "Graphs", "Queues"), "Strings/prefix search"),
                QuizQuestionModel("Segment tree is useful for?", listOf("Range queries", "Graph coloring", "Stack reverse", "Queue operations"), "Range queries"),
                QuizQuestionModel("Fenwick tree is also called?", listOf("Binary Indexed Tree", "Balanced Indexed Trie", "Binary Search Trie", "Indexed Heap"), "Binary Indexed Tree"),
                QuizQuestionModel("Diameter of tree means?", listOf("Longest path between two nodes", "Number of nodes", "Root value", "Tree width only"), "Longest path between two nodes")
            )
        }
    }
    //Dynamic programming
    private fun getDynamicProgrammingQuestions(level: String): List<QuizQuestionModel> {
        return when (level) {
            "Easy" -> listOf(
                QuizQuestionModel("Dynamic Programming is used for?", listOf("Optimizing overlapping subproblems", "Only sorting", "Only searching", "Only graphs"), "Optimizing overlapping subproblems"),
                QuizQuestionModel("DP stands for?", listOf("Dynamic Programming", "Data Processing", "Double Pointer", "Dynamic Pointer"), "Dynamic Programming"),
                QuizQuestionModel("DP stores results of?", listOf("Subproblems", "Only final problem", "Only arrays", "Only graphs"), "Subproblems"),
                QuizQuestionModel("Main idea of DP is?", listOf("Avoid repeated work", "Repeat more work", "Use only recursion", "Use only queue"), "Avoid repeated work"),
                QuizQuestionModel("Fibonacci can be optimized by?", listOf("Dynamic programming", "Queue", "Graph", "Heap"), "Dynamic programming"),
                QuizQuestionModel("DP is useful when problems have?", listOf("Overlapping subproblems", "No relation", "Only one step", "Only edges"), "Overlapping subproblems"),
                QuizQuestionModel("Top-down DP uses?", listOf("Recursion + memoization", "Queue only", "Stack only", "Heap only"), "Recursion + memoization"),
                QuizQuestionModel("Bottom-up DP uses?", listOf("Tabulation", "Only recursion", "Only BFS", "Only DFS"), "Tabulation"),
                QuizQuestionModel("Memoization means?", listOf("Store computed values", "Delete values", "Sort values", "Reverse values"), "Store computed values"),
                QuizQuestionModel("DP often improves?", listOf("Time complexity", "Only memory", "Only syntax", "Only output format"), "Time complexity")
            )
            "Medium" -> listOf(
                QuizQuestionModel("Tabulation fills table in?", listOf("Iterative manner", "Random order only", "Recursive only", "Queue order only"), "Iterative manner"),
                QuizQuestionModel("Memoization is a?", listOf("Top-down approach", "Bottom-up approach", "Greedy approach", "Divide only"), "Top-down approach"),
                QuizQuestionModel("Which classic problem uses DP?", listOf("0/1 Knapsack", "Queue traversal only", "BST insertion only", "Heapify only"), "0/1 Knapsack"),
                QuizQuestionModel("Longest Common Subsequence is solved by?", listOf("Dynamic programming", "Stack only", "Binary search only", "DFS only"), "Dynamic programming"),
                QuizQuestionModel("Coin change minimum coins is a?", listOf("DP problem", "Only graph problem", "Only tree problem", "Only queue problem"), "DP problem"),
                QuizQuestionModel("DP state represents?", listOf("Subproblem information", "Only final answer", "Input only", "Queue front"), "Subproblem information"),
                QuizQuestionModel("Transition in DP means?", listOf("Relation between states", "Deleting state", "Sorting state", "Reversing state"), "Relation between states"),
                QuizQuestionModel("Bottom-up DP avoids?", listOf("Recursive call overhead", "All loops", "All memory", "All inputs"), "Recursive call overhead"),
                QuizQuestionModel("Space optimization in DP is possible when?", listOf("Only previous states are needed", "All states always needed", "No state is needed", "Graph only"), "Only previous states are needed"),
                QuizQuestionModel("DP is not useful when subproblems are?", listOf("Independent", "Overlapping", "Repeated", "Optimal"), "Independent")
            )
            else -> listOf(
                QuizQuestionModel("0/1 Knapsack DP dimensions are usually based on?", listOf("Item index and capacity", "Only edges", "Only heap size", "Only queue size"), "Item index and capacity"),
                QuizQuestionModel("Matrix Chain Multiplication is a?", listOf("DP optimization problem", "Queue problem", "Heap problem", "Trie problem"), "DP optimization problem"),
                QuizQuestionModel("Longest Increasing Subsequence optimal approach can be?", listOf("DP with binary search", "Only BFS", "Only DFS", "Only stack"), "DP with binary search"),
                QuizQuestionModel("Bitmask DP is useful when?", listOf("State includes subsets", "Only arrays sorted", "Only queues", "Only strings fixed"), "State includes subsets"),
                QuizQuestionModel("Digit DP is commonly used for?", listOf("Counting numbers with conditions", "Tree balancing", "Graph coloring", "Heap insertion"), "Counting numbers with conditions"),
                QuizQuestionModel("DP on trees means?", listOf("Compute states on tree nodes", "Use queue only", "Convert tree to heap", "Only preorder"), "Compute states on tree nodes"),
                QuizQuestionModel("Rod cutting is similar to?", listOf("Unbounded knapsack", "BFS", "Heap sort", "DFS"), "Unbounded knapsack"),
                QuizQuestionModel("Minimum edit distance is solved using?", listOf("2D DP", "Stack", "Heap", "Queue"), "2D DP"),
                QuizQuestionModel("Optimal substructure means?", listOf("Optimal solution built from optimal subproblem solutions", "All answers are same", "Use queue always", "Sort first"), "Optimal solution built from optimal subproblem solutions"),
                QuizQuestionModel("When DP state design is wrong, result is usually?", listOf("Incorrect", "Always optimized", "Always faster", "Always constant"), "Incorrect")
            )
        }
    }

}