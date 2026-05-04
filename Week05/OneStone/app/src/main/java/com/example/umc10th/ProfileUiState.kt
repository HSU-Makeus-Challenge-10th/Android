package com.example.umc10th

import android.graphics.Bitmap

data class ProfileUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val profileBitmap: Bitmap? = null,
    val followingProfiles: List<FollowingProfile> = emptyList(),
    val errorMessage: String? = null
)
