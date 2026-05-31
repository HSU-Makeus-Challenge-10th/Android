package com.example.week10.data.model

import com.google.gson.annotations.SerializedName

data class UserData(
    val id: Int,
    val email: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val avatar: String,
) {
    val displayName: String get() = "$firstName $lastName"
}

data class UserResponse(
    val data: UserData,
)

data class UserListResponse(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val total: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val data: List<UserData>,
)
