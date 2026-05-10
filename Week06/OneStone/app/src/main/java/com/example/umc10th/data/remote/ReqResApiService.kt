package com.example.umc10th.data.remote

import com.example.umc10th.data.model.ReqResUsersResponse
import retrofit2.http.GET

interface ReqResApiService {
    @GET("api/users")
    suspend fun getUsers(): ReqResUsersResponse

    @GET("api/users/23")
    suspend fun getMissingUser(): ReqResUsersResponse
}
