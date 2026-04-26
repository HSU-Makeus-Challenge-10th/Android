package com.example.week02.core.network.interceptor

import com.example.week02.core.network.NetworkConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(
    private val apiKeyProvider: () -> String,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .header(NetworkConfig.ENV_HEADER, NetworkConfig.ENV_VALUE)

        apiKeyProvider()
            .trim()
            .takeIf { it.isNotEmpty() }
            ?.let { requestBuilder.header(NetworkConfig.API_KEY_HEADER, it) }

        return chain.proceed(requestBuilder.build())
    }
}
