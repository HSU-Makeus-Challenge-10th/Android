package com.example.week02.domain.repository

import com.example.week02.data.remote.reqres.ReqresUser

/** ReqRes API 원격 호출 담당 (Remote Repository). */
interface ReqresRemoteRepository {
    suspend fun getUser(userId: Int): ReqresUser
    suspend fun getFollowingUsers(page: Int = 2): List<ReqresUser>
}
