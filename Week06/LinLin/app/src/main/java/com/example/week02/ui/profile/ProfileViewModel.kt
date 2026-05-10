package com.example.week02.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week02.data.remote.reqres.ReqresUser
import com.example.week02.domain.repository.ReqresRemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val profileUser: ReqresUser? = null,
    val followingUsers: List<ReqresUser> = emptyList(),
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val remoteRepository: ReqresRemoteRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            runCatching { remoteRepository.getUser(1) }
                .onSuccess { user -> _uiState.update { it.copy(profileUser = user) } }
                .onFailure { e -> Log.e(TAG, "getUser failed", e) }
        }
        viewModelScope.launch {
            runCatching { remoteRepository.getFollowingUsers(page = 2) }
                .onSuccess { users -> _uiState.update { it.copy(followingUsers = users.take(8)) } }
                .onFailure { e -> Log.e(TAG, "listUsers failed", e) }
        }
    }

    companion object {
        private const val TAG = "ProfileViewModel"
    }
}
