package com.rundsa.app.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.rundsa.app.R
import com.rundsa.app.network.CodeRequest
import com.rundsa.app.network.RetrofitClient
import kotlinx.coroutines.launch

class CompilerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compiler)

        val etCode = findViewById<EditText>(R.id.etCode)
        val btnRun = findViewById<Button>(R.id.btnRunOnline)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)

        etCode.setText(
            """
            #include <stdio.h>

            int main() {
                printf("Hello World");
                return 0;
            }
            """.trimIndent()
        )

        btnRun.setOnClickListener {
            val codeInput = etCode.text.toString().trim()

            if (codeInput.isEmpty()) {
                Toast.makeText(this, "Enter code first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            tvOutput.text = "Running..."

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.api.runCode(
                        CodeRequest(
                            script = codeInput,
                            language = "c",
                            versionIndex = "5"
                        )
                    )

                    if (response.isSuccessful) {
                        val result = response.body()
                        tvOutput.text = result?.output ?: "No output"
                    } else {
                        tvOutput.text = "Error: ${response.code()}"
                    }
                } catch (e: Exception) {
                    tvOutput.text = "Failed: ${e.message}"
                }
            }
        }
    }
}