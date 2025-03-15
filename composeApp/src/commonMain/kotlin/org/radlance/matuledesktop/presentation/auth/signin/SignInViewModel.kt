package org.radlance.matuledesktop.presentation.auth.signin

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

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val mapper: AuthResult.Mapper<AuthResultUiState>,
    validate: Validate,
) : AuthViewModel(validate) {
    private val _signInResultUiState =
        MutableStateFlow<AuthResultUiState>(AuthResultUiState.Initial)
    val signInResultUiState: StateFlow<AuthResultUiState>
        get() = _signInResultUiState.asStateFlow()

    fun signIn(email: String, password: String) {
        validateFields(email = email, password = password)
        with(authUiState.value) {
            if (isCorrectEmail && isCorrectPassword) {
                viewModelScope.launch {
                    val user = User(email = email, password = password)
                    _signInResultUiState.value = AuthResultUiState.Loading()
                    val result = authRepository.signIn(user)
                    _signInResultUiState.value = result.map(mapper)
                }
            }
        }
    }
}