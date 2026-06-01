package com.example.deku.feature.profile.data.remote

import com.example.deku.feature.profile.data.remote.dto.SingleUserResponseDto
import com.example.deku.feature.profile.data.remote.dto.UserListResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProfileApiService {

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
