package com.example.yido.data.repository

import com.example.yido.data.model.UserListResponse
import com.example.yido.data.model.UserResponse
import com.example.yido.data.remote.ReqResService
import com.example.yido.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val reqResService: ReqResService
) : UserRepository {

    override suspend fun getUserProfile(userId: Int): Response<UserResponse> {
        return withContext(Dispatchers.IO) {
            reqResService.getUserProfile(userId)
        }
    }

    override suspend fun getUserList(page: Int): Response<UserListResponse> {
        return withContext(Dispatchers.IO) {
            reqResService.getUserList(page)
        }
    }
}
