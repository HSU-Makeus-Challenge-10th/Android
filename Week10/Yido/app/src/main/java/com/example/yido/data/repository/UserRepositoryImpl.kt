package com.example.yido.data.repository

import com.example.yido.data.model.UserListResponse
import com.example.yido.data.model.UserResponse
import com.example.yido.data.remote.ReqResService
import com.example.yido.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val reqResService: ReqResService
) : UserRepository {

    override suspend fun getUserProfile(userId: Int): Result<UserResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = reqResService.getUserProfile(userId)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("API error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getUserList(page: Int): Result<UserListResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = reqResService.getUserList(page)
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("API error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
