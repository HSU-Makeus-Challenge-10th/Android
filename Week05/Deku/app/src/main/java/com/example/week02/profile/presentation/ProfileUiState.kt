package com.example.week02.profile.presentation

import com.example.week02.profile.data.remote.dto.ReqResUserDto

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
