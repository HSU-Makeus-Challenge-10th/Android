package com.example.week02.data.remote.reqres

class ReqresRepository(
    private val api: ReqresApi = ReqresApiClient.api,
) {
    suspend fun getUser(userId: Int): ReqresUser = api.getUser(userId).data

    suspend fun getFollowingUsers(page: Int = 2): List<ReqresUser> = api.listUsers(page).data
}

