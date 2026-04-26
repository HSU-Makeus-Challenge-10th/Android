package com.example.umc10th

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ReqResRetrofitClient {
    private const val API_KEY = "reqres_3eb039d9ecd24873b53b35fb4d3ad155"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(ReqResLoggingInterceptor())
            .build()
    }

    val api: ReqResApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ReqResApiService::class.java)
    }
}
