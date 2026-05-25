package com.example.week09.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week09.data.model.UserData
import com.example.week09.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MyPageUiState(
    val myProfile: UserData? = null,
    val followingList: List<UserData> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
)

class MyPageViewModel(
    private val userRepository: UserRepository = UserRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val myUserId = 1

    init {
        loadMyPage()
    }

    private fun loadMyPage() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val profileResult = userRepository.getUserProfile(myUserId)
            val followingResult = userRepository.getFollowingUsers(page = 1, excludeUserId = myUserId)

            profileResult
                .onSuccess { user ->
                    _uiState.update { it.copy(myProfile = user) }
                }
                .onFailure { e ->
                    Log.e(TAG, "Profile load failed", e)
                    _uiState.update { it.copy(errorMessage = e.message) }
                }

            followingResult
                .onSuccess { users ->
                    _uiState.update { it.copy(followingList = users) }
                }
                .onFailure { e ->
                    Log.e(TAG, "Following load failed", e)
                    if (_uiState.value.errorMessage == null) {
                        _uiState.update { it.copy(errorMessage = e.message) }
                    }
                }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    companion object {
        private const val TAG = "MyPageViewModel"
    }
}
