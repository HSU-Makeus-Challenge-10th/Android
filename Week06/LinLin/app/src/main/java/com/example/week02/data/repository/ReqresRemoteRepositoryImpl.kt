package com.example.week02.data.repository

import com.example.week02.data.remote.reqres.ReqresApi
import com.example.week02.data.remote.reqres.ReqresUser
import com.example.week02.domain.repository.ReqresRemoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReqresRemoteRepositoryImpl @Inject constructor(
    private val api: ReqresApi,
) : ReqresRemoteRepository {

    override suspend fun getUser(userId: Int): ReqresUser = api.getUser(userId).data

    override suspend fun getFollowingUsers(page: Int): List<ReqresUser> =
        api.listUsers(page).data
}
