package com.example.deku.feature.profile.data

import com.example.deku.feature.profile.data.remote.ProfileApiService
import com.example.deku.feature.profile.data.remote.dto.ReqResUserDto
import com.example.deku.feature.profile.model.ProfileUser
import retrofit2.Response

class ProfileRepository(
    private val service: ProfileApiService,
) {
    suspend fun getProfile(userId: Int): Result<ProfileUser> {
        return runCatchingResponse(
            call = { service.getUser(userId) },
            emptyBodyMessage = "유저 정보를 받아오지 못했습니다.",
        ).fold(
            onSuccess = { body ->
                body.data?.toProfileUser()?.let(Result.Companion::success)
                    ?: Result.failure(RuntimeException("유저 데이터가 비어 있습니다."))
            },
            onFailure = Result.Companion::failure,
        )
    }

    suspend fun getFollowing(page: Int = 1): Result<List<ProfileUser>> {
        return runCatchingResponse(
            call = { service.getUsers(page = page) },
            emptyBodyMessage = "팔로잉 목록을 받아오지 못했습니다.",
        ).fold(
            onSuccess = { body -> Result.success(body.data.map { it.toProfileUser() }) },
            onFailure = Result.Companion::failure,
        )
    }

    private suspend fun <T> runCatchingResponse(
        call: suspend () -> Response<T>,
        emptyBodyMessage: String,
    ): Result<T> {
        return try {
            val response = call()

            if (response.isSuccessful) {
                response.body()?.let(Result.Companion::success)
                    ?: Result.failure(RuntimeException(emptyBodyMessage))
            } else {
                val errorMessage = response.errorBody()
                    ?.string()
                    ?.takeIf { it.isNotBlank() }
                    ?: response.message().ifBlank { "알 수 없는 네트워크 오류가 발생했습니다." }

                Result.failure(RuntimeException("HTTP ${response.code()}: $errorMessage"))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    private fun ReqResUserDto.toProfileUser(): ProfileUser {
        return ProfileUser(
            id = id,
            name = fullName,
            email = email,
            avatarUrl = avatar,
        )
    }
}
