package com.example.yido.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yido.data.model.UserData
import com.example.yido.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val myProfile: UserData? = null,
    val followingList: List<UserData> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    private val myUserId = 1

    init {
        fetchMyProfile()
        fetchFollowingList()
    }

    private fun fetchMyProfile() {
        viewModelScope.launch {
            userRepository.getUserProfile(myUserId)
                .onSuccess { userResponse ->
                    _uiState.value = _uiState.value.copy(myProfile = userResponse.data)
                }
                .onFailure { error ->
                    Log.e(TAG, "Profile load failed: ${error.message}")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Failed to load profile."
                    )
                }
        }
    }

    private fun fetchFollowingList() {
        viewModelScope.launch {
            userRepository.getUserList(1)
                .onSuccess { userListResponse ->
                    val filtered = userListResponse.data.filter { it.id != myUserId }
                    _uiState.value = _uiState.value.copy(followingList = filtered)
                }
                .onFailure { error ->
                    Log.e(TAG, "Following list load failed: ${error.message}")
                }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
