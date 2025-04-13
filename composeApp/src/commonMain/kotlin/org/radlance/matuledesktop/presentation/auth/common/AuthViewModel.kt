package org.radlance.matuledesktop.presentation.auth.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AuthViewModel(
    private val validation: Validate
) : ViewModel() {
    private val authUiStateMutable = MutableStateFlow(AuthUiState())
    val authUiState: StateFlow<AuthUiState>
        get() = authUiStateMutable.asStateFlow()


    fun validateFields(
        name: String? = null,
        email: String? = null,
        password: String? = null
    ) {
        authUiStateMutable.update { currentState ->
            currentState.copy(
                nameErrorMessage = name?.let { validation.validName(it) } ?: "",
                emailErrorMessage = email?.let { validation.validEmail(it) } ?: "",
                passwordErrorMessage = password?.let { validation.validPassword(password) } ?: ""
            )
        }
    }

    fun resetEmailError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(emailErrorMessage = "")
        }
    }

    fun resetPasswordError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(passwordErrorMessage = "")
        }
    }

    fun resetNameError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(nameErrorMessage = "")
        }
    }
}