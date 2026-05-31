package com.example.umc10th.data.repository

import com.example.umc10th.data.model.ReqResUser
import com.example.umc10th.data.remote.ReqResApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepositoryImpl @Inject constructor(
    private val reqResApiService: ReqResApiService
) : ProfileRepository {
    // ReqRes API에서 사용자 목록을 조회한다.
    override suspend fun getUsers(): List<ReqResUser> {
        return reqResApiService.getUsers().data
    }
}
