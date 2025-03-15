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
                isCorrectName = name?.let { validation.validName(it) } ?: true,
                isCorrectEmail = email?.let { validation.validEmail(it) } ?: true,
                isCorrectPassword = password?.let { validation.validPassword(password) } ?: true
            )
        }
    }

    fun resetEmailError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectEmail = true)
        }
    }

    fun resetPasswordError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectPassword = true)
        }
    }

    fun resetNameError() {
        authUiStateMutable.update { currentState ->
            currentState.copy(isCorrectName = true)
        }
    }
}