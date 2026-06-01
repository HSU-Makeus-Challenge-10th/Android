package com.example.umc10th.data.repository

import com.example.umc10th.data.model.ReqResUser

interface ProfileRepository {
    suspend fun getUsers(): List<ReqResUser>
}
