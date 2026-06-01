package com.example.umc10th.di

import com.example.umc10th.data.remote.ReqResApiService
import com.example.umc10th.data.remote.ReqResLoggingInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val REQ_RES_BASE_URL = "https://reqres.in/"
    private const val REQ_RES_API_KEY = "reqres_3eb039d9ecd24873b53b35fb4d3ad155"

    // 앱 전체에서 공유할 OkHttpClient를 제공한다.
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: ReqResLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("x-api-key", REQ_RES_API_KEY)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // ReqRes API 호출에 사용할 Retrofit 인스턴스를 제공한다.
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(REQ_RES_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 사용자 프로필 조회에 사용할 API 서비스를 제공한다.
    @Provides
    @Singleton
    fun provideReqResApiService(retrofit: Retrofit): ReqResApiService {
        return retrofit.create(ReqResApiService::class.java)
    }
}
