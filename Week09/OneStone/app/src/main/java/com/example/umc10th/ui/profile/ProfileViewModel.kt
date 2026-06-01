package com.example.umc10th.ui.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.umc10th.data.repository.ProfileRepository
import com.example.umc10th.ui.base.BaseViewModel
import com.example.umc10th.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch
import retrofit2.HttpException

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : BaseViewModel<ProfileUiState>(ProfileUiState()) {

    fun loadUserProfile() {
        if (currentState.userName.isNotEmpty()) return

        viewModelScope.launch {
            setState { copy(isLoading = true, errorMessage = null) }

            runCatching {
                val users = profileRepository.getUsers()
                val user = users.firstOrNull { it.id == USER_ID }
                    ?: error("User id $USER_ID not found")
                val followingProfiles = users
                    .filter { it.id != USER_ID }
                    .map { followingUser ->
                        FollowingProfileUiState(
                            id = followingUser.id,
                            name = "${followingUser.firstName} ${followingUser.lastName}",
                            avatarUrl = followingUser.avatar
                        )
                    }

                ProfileUiState(
                    isLoading = false,
                    userName = "${user.firstName} ${user.lastName}",
                    profileImageUrl = user.avatar,
                    followingProfiles = followingProfiles
                )
            }.onSuccess { loadedState ->
                setState { loadedState }
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to load user profile", throwable)
                setState {
                    copy(
                        isLoading = false,
                        errorMessage = throwable.toProfileErrorMessage()
                    )
                }
            }
        }
    }

    private fun Throwable.toProfileErrorMessage(): String? {
        if (this !is HttpException) return message

        return when (code()) {
            in 400..499 -> "Client error"
            in 500..599 -> "Server error"
            else -> message
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
        private const val USER_ID = 1
    }
}

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val profileImageUrl: String? = null,
    val followingProfiles: List<FollowingProfileUiState> = emptyList(),
    val errorMessage: String? = null
) : UiState

data class FollowingProfileUiState(
    val id: Int,
    val name: String,
    val avatarUrl: String
)
