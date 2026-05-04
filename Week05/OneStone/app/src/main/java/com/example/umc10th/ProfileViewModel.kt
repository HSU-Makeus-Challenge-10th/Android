package com.example.umc10th

import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.URL

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadUserProfile() {
        if (_uiState.value.userName.isNotEmpty()) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            runCatching {
                val users = ReqResRetrofitClient.api.getUsers().data
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
                _uiState.value = loadedState
            }.onFailure { throwable ->
                Log.e(TAG, "Failed to load user profile", throwable)
                _uiState.update {
                    it.copy(
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
