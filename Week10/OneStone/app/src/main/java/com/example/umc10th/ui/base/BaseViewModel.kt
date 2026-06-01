package com.example.umc10th.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiState

abstract class BaseViewModel<STATE : UiState>(
    initialState: STATE
) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<STATE> = _uiState.asStateFlow()

    protected val currentState: STATE
        get() = _uiState.value

    protected fun setState(reducer: STATE.() -> STATE) {
        _uiState.update { currentState ->
            currentState.reducer()
        }
    }
}
