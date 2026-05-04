package com.example.yido.domain.repository

import com.example.yido.data.model.UserListResponse
import com.example.yido.data.model.UserResponse
import retrofit2.Response

interface UserRepository {
    suspend fun getUserProfile(userId: Int): Response<UserResponse>
    suspend fun getUserList(page: Int = 1): Response<UserListResponse>
}
