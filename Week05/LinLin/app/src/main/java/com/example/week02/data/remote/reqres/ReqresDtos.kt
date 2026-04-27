package com.example.week02.data.remote.reqres

import com.google.gson.annotations.SerializedName

data class ReqresUserResponse(
    @SerializedName("data") val data: ReqresUser,
)

data class ReqresListUsersResponse(
    @SerializedName("data") val data: List<ReqresUser>,
)

data class ReqresUser(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("avatar") val avatar: String,
) {
    val displayName: String get() = "$firstName $lastName"
}

