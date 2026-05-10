package com.example.week02.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week02.BuildConfig
import com.example.week02.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Idle)
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun loadProfileScreen(
        userId: Int = DEFAULT_USER_ID,
        page: Int = DEFAULT_PAGE,
    ) {
        if (BuildConfig.REQRES_API_KEY.isBlank()) {
            _uiState.value = ProfileUiState.Error(
                "local.properties 또는 환경 변수에 REQRES_API_KEY를 추가하세요."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = ProfileUiState.Loading

            val userResult = repository.getProfile(userId)
            if (userResult.isFailure) {
                _uiState.value = ProfileUiState.Error(
                    userResult.exceptionOrNull().toUserMessage()
                )
                return@launch
            }
            val user = userResult.getOrNull() ?: run {
                _uiState.value = ProfileUiState.Error("프로필 정보를 불러오지 못했습니다.")
                return@launch
            }

            val followingResult = repository.getFollowing(page)
            if (followingResult.isFailure) {
                _uiState.value = ProfileUiState.Error(
                    followingResult.exceptionOrNull().toUserMessage()
                )
                return@launch
            }
            val followingUsers = followingResult.getOrNull().orEmpty()

            _uiState.value = ProfileUiState.Success(
                user = user,
                followingUsers = followingUsers.filterNot { it.id == user.id },
            )
        }
    }

    private fun Throwable?.toUserMessage(): String {
        return this?.message?.takeIf { it.isNotBlank() }
            ?: "프로필 정보를 불러오지 못했습니다."
    }

    companion object {
        private const val DEFAULT_USER_ID = 1
        private const val DEFAULT_PAGE = 1
    }
}
