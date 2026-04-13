package com.rundsa.app.network

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class CodeRequest(
    val script: String,
    val language: String,
    val versionIndex: String
)

data class CodeResponse(
    val output: String?,
    val error: String? = null
)

interface CompilerApi {
    @POST("run-code")
    suspend fun runCode(@Body request: CodeRequest): Response<CodeResponse>
}