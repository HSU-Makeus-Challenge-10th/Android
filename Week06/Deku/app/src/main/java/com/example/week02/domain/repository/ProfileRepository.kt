package com.example.week02.domain.repository

import com.example.week02.data.model.profile.ReqResUserDto

interface ProfileRepository {
    suspend fun getProfile(userId: Int): Result<ReqResUserDto>
    suspend fun getFollowing(page: Int = 1): Result<List<ReqResUserDto>>
}
