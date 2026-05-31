package com.example.week10.ui.mypage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week10.data.model.UserData
import com.example.week10.data.repository.UserRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface MyPageUiState {
    data object Loading : MyPageUiState

    data class Success(
        val myProfile: UserData?,
        val followingList: List<UserData>,
    ) : MyPageUiState

    data class Error(val message: String) : MyPageUiState
}

class MyPageViewModel(
    private val userRepository: UserRepository = UserRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow<MyPageUiState>(MyPageUiState.Loading)
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    private val myUserId = 1

    init {
        loadMyPage()
    }

    fun retry() {
        loadMyPage()
    }

    private fun loadMyPage() {
        viewModelScope.launch {
            _uiState.value = MyPageUiState.Loading

            runCatching {
                coroutineScope {
                    val profileDeferred = async { userRepository.getUserProfile(myUserId) }
                    val followingDeferred = async {
                        userRepository.getFollowingUsers(page = 1, excludeUserId = myUserId)
                    }

                    val profileResult = profileDeferred.await()
                    val followingResult = followingDeferred.await()

                    val profile = profileResult.getOrElse { throw it }
                    val following = followingResult.getOrElse { throw it }

                    profile to following
                }
            }.onSuccess { (profile, following) ->
                _uiState.value = MyPageUiState.Success(
                    myProfile = profile,
                    followingList = following,
                )
            }.onFailure { e ->
                Log.e(TAG, "My page load failed", e)
                _uiState.value = MyPageUiState.Error(
                    message = e.message ?: "데이터를 불러오지 못했습니다.",
                )
            }
        }
    }

    companion object {
        private const val TAG = "MyPageViewModel"
    }
}
