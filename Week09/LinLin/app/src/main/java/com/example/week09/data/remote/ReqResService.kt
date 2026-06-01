package com.example.week09.data.remote

import com.example.week09.data.model.UserListResponse
import com.example.week09.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResService {

    @GET("api/users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Int,
    ): Response<UserResponse>

    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int = 1,
    ): Response<UserListResponse>
}
