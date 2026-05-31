package com.example.umc10th.data.remote

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class ReqResLoggingInterceptor @Inject constructor() : Interceptor {
    // ReqRes 요청과 응답 정보를 로그로 확인한다.
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val maskedApiKey = request.header("x-api-key")?.let { maskApiKey(it) }

        Log.d(TAG, "Request ${request.method} ${request.url}")
        Log.d(TAG, "Header x-api-key=$maskedApiKey")

        ReqResDebugConfig.forceHttpErrorCode?.let { forcedCode ->
            val response = Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(forcedCode)
                .message("Forced debug HTTP error")
                .body("""{"error":"forced debug error"}"""
                    .toResponseBody("application/json".toMediaType()))
                .build()

            Log.d(TAG, "Forced response code=${response.code} url=${response.request.url}")
            return response
        }

        val response = chain.proceed(request)
        Log.d(TAG, "Response code=${response.code} url=${response.request.url}")

        return response
    }

    // API 키가 로그에 그대로 노출되지 않도록 마스킹한다.
    private fun maskApiKey(apiKey: String): String {
        if (apiKey.length <= 8) return "*".repeat(apiKey.length)
        return "${apiKey.take(8)}...${apiKey.takeLast(4)}"
    }

    companion object {
        private const val TAG = "ReqResApi"
    }
}
