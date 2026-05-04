package com.example.yido

import com.google.gson.annotations.SerializedName

// 개별 유저 데이터
data class UserData(
    val id: Int,
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String
)

// 단일 유저 조회 API 응답 (/api/users/{id})
data class UserResponse(
    val data: UserData
)

// 유저 목록 조회 API 응답 (/api/users?page=1)
data class UserListResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: List<UserData>
)
