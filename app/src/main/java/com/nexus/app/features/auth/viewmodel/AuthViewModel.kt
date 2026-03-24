package com.nexus.app.features.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexus.app.core.data.remote.AuthRepository
import com.nexus.app.core.data.remote.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val email: String = "",
    val password: String = "",
    val isSignUp: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isAuthenticated: Boolean = false
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state: StateFlow<AuthUiState> = _state

    init {
        // Check if already signed in
        if (authRepository.isSignedIn()) {
            _state.value = _state.value.copy(isAuthenticated = true)
        }
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, errorMessage = null)
    }

    fun toggleMode() {
        _state.value = _state.value.copy(
            isSignUp = !_state.value.isSignUp,
            errorMessage = null
        )
    }

    fun submit() {
        val s = _state.value
        if (s.email.isBlank() || s.password.isBlank()) {
            _state.value = s.copy(errorMessage = "Email and password are required")
            return
        }
        if (s.password.length < 6) {
            _state.value = s.copy(errorMessage = "Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _state.value = s.copy(isLoading = true, errorMessage = null)
            val result = if (s.isSignUp) {
                authRepository.signUp(s.email, s.password)
            } else {
                authRepository.signIn(s.email, s.password)
            }
            when (result) {
                is AuthResult.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isAuthenticated = true
                    )
                }
                is AuthResult.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _state.value = AuthUiState()
        }
    }
}
