package org.radlance.matuledesktop.presentation.auth.common

data class AuthUiState(
    val isCorrectName: Boolean = true,
    val isCorrectEmail: Boolean = true,
    val isCorrectPassword: Boolean = true
)
