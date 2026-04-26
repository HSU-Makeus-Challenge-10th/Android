package com.example.week02.profile.data.remote.service

import com.example.week02.profile.data.remote.dto.SingleUserResponseDto
import com.example.week02.profile.data.remote.dto.UserListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileService {

    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") userId: Int,
    ): Response<SingleUserResponseDto>

    @GET("users")
    suspend fun getUsers(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 8,
    ): Response<UserListResponseDto>
}
