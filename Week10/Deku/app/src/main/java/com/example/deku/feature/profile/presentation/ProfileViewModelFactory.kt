package com.example.deku.feature.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.deku.core.network.RetrofitProvider
import com.example.deku.feature.profile.data.ProfileRepository

class ProfileViewModelFactory(
    private val repository: ProfileRepository = ProfileRepository(RetrofitProvider.profileService),
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
