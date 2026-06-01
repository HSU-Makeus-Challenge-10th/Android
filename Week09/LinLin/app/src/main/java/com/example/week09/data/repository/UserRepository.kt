package com.example.week09.data.repository

import com.example.week09.data.NetworkClient
import com.example.week09.data.model.UserData
import com.example.week09.data.remote.ReqResService

class UserRepository(
    private val service: ReqResService = NetworkClient.reqResService,
) {

    suspend fun getUserProfile(userId: Int): Result<UserData> = runCatching {
        val response = service.getUserProfile(userId)
        if (!response.isSuccessful) {
            error("Failed to load profile: ${response.code()}")
        }
        response.body()?.data ?: error("Empty profile response")
    }

    suspend fun getFollowingUsers(page: Int = 1, excludeUserId: Int): Result<List<UserData>> =
        runCatching {
            val response = service.getUserList(page)
            if (!response.isSuccessful) {
                error("Failed to load users: ${response.code()}")
            }
            response.body()?.data?.filter { it.id != excludeUserId }.orEmpty()
        }
}
