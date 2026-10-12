package com.example.mini_project.ui.login

import android.util.Patterns
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.mini_project.data.repo.AuthRepo
import com.example.mini_project.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repo: AuthRepo
) : BaseViewModel() {

    private val _uiState =
        MutableStateFlow<LoginUiState>(LoginUiState.Idle)

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        if (repo.isLoggedIn()) {
            _uiState.value = LoginUiState.Success
        }
    }

    fun login(email: String, password: String) {
        if (_uiState.value == LoginUiState.Loading ||
            _uiState.value == LoginUiState.Success
        ) {
            return
        }

        val trimmedEmail = email.trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
            _uiState.value = LoginUiState.Error(
                "Please enter a valid email address."
            )
            return
        }

        if (password.isEmpty()) {
            _uiState.value = LoginUiState.Error(
                "Please enter your password."
            )
            return
        }

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            val result = safeApiCall {
                repo.login(trimmedEmail, password)
            }

            _uiState.value = if (result != null) {
                LoginUiState.Success
            } else {
                LoginUiState.Idle
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(repo = AuthRepo.getInstance())
            }
        }
    }
}

sealed interface LoginUiState {
    object Idle : LoginUiState
    object Loading : LoginUiState
    object Success : LoginUiState
    data class Error(val message: String) : LoginUiState
}