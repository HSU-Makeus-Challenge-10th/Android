package com.example.umc10th.ui.profile

import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.umc10th.data.model.FollowingProfile
import com.example.umc10th.data.repository.ProfileRepository
import com.example.umc10th.ui.base.BaseViewModel
import com.example.umc10th.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.URL

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
                val user = users.firstOrNull { it.id == 1 }
                    ?: error("User id 1 not found")

                val profileBitmap = loadBitmap(user.avatar)
                val followingProfiles = users
                    .filter { it.id != 1 }
                    .map { followingUser ->
                        async {
                            FollowingProfile(
                                id = followingUser.id,
                                avatarBitmap = loadBitmap(followingUser.avatar)
                            )
                        }
                    }
                    .awaitAll()

                ProfileUiState(
                    isLoading = false,
                    userName = "${user.firstName} ${user.lastName}",
                    profileBitmap = profileBitmap,
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
            in 400..499 -> "내잘못"
            in 500..599 -> "니잘못"
            else -> message
        }
    }

    private suspend fun loadBitmap(imageUrl: String) = withContext(Dispatchers.IO) {
        URL(imageUrl).openStream().use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val profileBitmap: Bitmap? = null,
    val followingProfiles: List<FollowingProfile> = emptyList(),
    val errorMessage: String? = null
) : UiState
