package com.example.yido.data.remote

import com.example.yido.data.model.UserListResponse
import com.example.yido.data.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqResService {

    @GET("api/users/{id}")
    suspend fun getUserProfile(
        @Path("id") userId: Int
    ): Response<UserResponse>

    @GET("api/users")
    suspend fun getUserList(
        @Query("page") page: Int = 1
    ): Response<UserListResponse>
}
