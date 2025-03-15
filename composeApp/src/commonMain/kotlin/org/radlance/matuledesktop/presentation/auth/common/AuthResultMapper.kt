package org.radlance.matuledesktop.presentation.auth.common

import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.account_already_exist
import matuledesktop.composeapp.generated.resources.incorrect_data_error
import matuledesktop.composeapp.generated.resources.no_connection
import matuledesktop.composeapp.generated.resources.unknown_error
import matuledesktop.composeapp.generated.resources.verification_error
import org.radlance.matuledesktop.domain.auth.AuthResult

class AuthResultMapper : AuthResult.Mapper<AuthResultUiState> {
    override fun mapSuccess(): AuthResultUiState {
        return AuthResultUiState.Success
    }

    override fun mapError(noConnection: Boolean, statusCode: Int): AuthResultUiState {
        val messageResId = if (noConnection) {
            Res.string.no_connection
        } else when (statusCode) {
            400 -> Res.string.incorrect_data_error
            422 -> Res.string.account_already_exist
            403 -> Res.string.verification_error
            else -> Res.string.unknown_error
        }

        return AuthResultUiState.Error(messageResId)
    }
}