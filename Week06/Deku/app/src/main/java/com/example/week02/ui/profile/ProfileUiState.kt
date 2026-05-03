package com.example.week02.ui.profile

import com.example.week02.data.model.profile.ReqResUserDto

sealed interface ProfileUiState {
    object Idle : ProfileUiState
    object Loading : ProfileUiState

    data class Success(
        val user: ReqResUserDto,
        val followingUsers: List<ReqResUserDto> = emptyList(),
    ) : ProfileUiState

    data class Error(
        val message: String,
    ) : ProfileUiState
}
