package com.example.week02.data.remote.reqres

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReqresApi {
    @GET("users/{id}")
    suspend fun getUser(
        @Path("id") id: Int,
    ): ReqresUserResponse

    @GET("users")
    suspend fun listUsers(
        @Query("page") page: Int,
    ): ReqresListUsersResponse
}

