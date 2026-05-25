package com.example.umc10th.data.repository

import com.example.umc10th.data.model.ReqResUser
import com.example.umc10th.data.remote.ReqResRetrofitClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor() : ProfileRepository {
    override suspend fun getUsers(): List<ReqResUser> {
        return ReqResRetrofitClient.api.getUsers().data
    }
}
