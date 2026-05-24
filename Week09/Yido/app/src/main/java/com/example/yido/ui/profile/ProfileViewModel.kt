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
            try {
                val response = userRepository.getUserProfile(myUserId)
                if (response.isSuccessful) {
                    val user = response.body()?.data
                    _uiState.value = _uiState.value.copy(myProfile = user)
                } else {
                    Log.e(TAG, "API fail: ${response.code()}")
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Failed to load profile."
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error: ${e.message}")
                _uiState.value = _uiState.value.copy(
                    errorMessage = "Network error occurred."
                )
            }
        }
    }

    private fun fetchFollowingList() {
        viewModelScope.launch {
            try {
                val response = userRepository.getUserList(1)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        val filtered = body.data.filter { it.id != myUserId }
                        _uiState.value = _uiState.value.copy(followingList = filtered)
                    }
                } else {
                    Log.e(TAG, "API fail: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error: ${e.message}")
            }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
