package com.example.week02.profile.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ReqResUserDto(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("email")
    val email: String = "",
    @SerializedName("first_name")
    val firstName: String = "",
    @SerializedName("last_name")
    val lastName: String = "",
    @SerializedName("avatar")
    val avatar: String = "",
) {
    val fullName: String
        get() = listOf(firstName, lastName)
            .filter { it.isNotBlank() }
            .joinToString(" ")
            .ifBlank { email.ifBlank { "Unknown User" } }
}

data class SupportDto(
    @SerializedName("url")
    val url: String = "",
    @SerializedName("text")
    val text: String = "",
)

data class SingleUserResponseDto(
    @SerializedName("data")
    val data: ReqResUserDto? = null,
    @SerializedName("support")
    val support: SupportDto? = null,
)

data class UserListResponseDto(
    @SerializedName("page")
    val page: Int = 0,
    @SerializedName("per_page")
    val perPage: Int = 0,
    @SerializedName("total")
    val total: Int = 0,
    @SerializedName("total_pages")
    val totalPages: Int = 0,
    @SerializedName("data")
    val data: List<ReqResUserDto> = emptyList(),
    @SerializedName("support")
    val support: SupportDto? = null,
)
