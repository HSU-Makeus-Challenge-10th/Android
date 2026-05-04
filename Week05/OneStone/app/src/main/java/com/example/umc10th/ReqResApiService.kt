package com.example.umc10th

import retrofit2.http.GET

interface ReqResApiService {
    @GET("api/users")
    suspend fun getUsers(): ReqResUsersResponse

    @GET("api/users/23")
    suspend fun getMissingUser(): ReqResUsersResponse
}
