package com.example.yido.domain.repository

import com.example.yido.data.model.UserListResponse
import com.example.yido.data.model.UserResponse

interface UserRepository {
    suspend fun getUserProfile(userId: Int): Result<UserResponse>
    suspend fun getUserList(page: Int = 1): Result<UserListResponse>
}
