package org.radlance.matuledesktop.presentation.auth.signup

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.radlance.matuledesktop.domain.auth.AuthRepository
import org.radlance.matuledesktop.domain.auth.AuthResult
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.presentation.auth.common.AuthResultUiState
import org.radlance.matuledesktop.presentation.auth.common.AuthViewModel
import org.radlance.matuledesktop.presentation.auth.common.Validate

class SignUpViewModel(
    private val authRepository: AuthRepository,
    private val mapper: AuthResult.Mapper<AuthResultUiState>,
    validate: Validate,
) : AuthViewModel(validate) {
    private val _signUpResultUiState =
        MutableStateFlow<AuthResultUiState>(AuthResultUiState.Initial)
    val signUpResultUiState: StateFlow<AuthResultUiState>
        get() = _signUpResultUiState.asStateFlow()

    fun signUp(name: String, email: String, password: String) {
        validateFields(name = name, email = email, password = password)
        with(authUiState.value) {
            if (isCorrectEmail && isCorrectPassword) {
                viewModelScope.launch {
                    val user = User(firstName = name, email = email, password = password)
                    _signUpResultUiState.value = AuthResultUiState.Loading()
                    val result = authRepository.signUp(user)
                    _signUpResultUiState.value = result.map(mapper)
                }
            }
        }
    }
}