package com.rundsa.app.activities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

            if (!isInternetAvailable(this)) {
                AlertDialog.Builder(this)
                    .setTitle("No Internet")
                    .setMessage("Please turn on your internet")
                    .setPositiveButton("OK", null)
                    .show()
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
                        val output = result?.output?.trim()
                        val error = result?.error?.trim()?.lowercase()

                        if (!error.isNullOrEmpty()) {
                            if (
                                error.contains("limit") ||
                                error.contains("credit") ||
                                error.contains("quota") ||
                                error.contains("exceeded")
                            ) {
                                tvOutput.text = "Daily limit reached. Try again tomorrow"

                                AlertDialog.Builder(this@CompilerActivity)
                                    .setTitle("Limit Reached")
                                    .setMessage("You have used all 20 runs for today. Please try again tomorrow.")
                                    .setPositiveButton("OK", null)
                                    .show()
                            } else {
                                tvOutput.text = result.error ?: "Error in code"
                            }
                        } else if (!output.isNullOrEmpty()) {
                            tvOutput.text = output
                        } else {
                            tvOutput.text = "No output"
                        }

                    } else {
                        val errorText = response.errorBody()?.string()?.lowercase() ?: ""

                        if (
                            response.code() == 429 ||
                            errorText.contains("limit") ||
                            errorText.contains("credit") ||
                            errorText.contains("quota") ||
                            errorText.contains("exceeded")
                        ) {
                            tvOutput.text = "Daily limit reached. Try again tomorrow"

                            AlertDialog.Builder(this@CompilerActivity)
                                .setTitle("Limit Reached")
                                .setMessage("You have used all 20 runs for today. Please try again tomorrow.")
                                .setPositiveButton("OK", null)
                                .show()
                        } else {
                            tvOutput.text = "Error: ${response.code()}"
                        }
                    }

                } catch (e: Exception) {
                    tvOutput.text = "Failed: Check internet connection"
                }
            }
        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }
}