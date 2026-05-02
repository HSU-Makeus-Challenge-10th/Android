package com.example.deku.feature.profile.presentation

import com.example.deku.feature.profile.model.ProfileUser

sealed interface ProfileUiState {
    object Idle : ProfileUiState
    object Loading : ProfileUiState

    data class Success(
        val user: ProfileUser,
        val followingUsers: List<ProfileUser> = emptyList(),
    ) : ProfileUiState

    data class Error(
        val message: String,
    ) : ProfileUiState
}
