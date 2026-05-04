package com.example.yido

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    private const val BASE_URL = "https://reqres.in/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 워크북 지침에 따라 x-api-key 헤더에 API 키 추가
    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("x-api-key", "reqres_3cc917778886402cb49885a9215fb6ff")
            .build()
        chain.proceed(newRequest)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val reqResService: ReqResService by lazy {
        retrofit.create(ReqResService::class.java)
    }
}
