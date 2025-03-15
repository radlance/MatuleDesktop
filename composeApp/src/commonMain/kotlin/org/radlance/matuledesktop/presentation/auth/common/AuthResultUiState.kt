package org.radlance.matuledesktop.presentation.auth.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

interface AuthResultUiState {

    @Composable
    fun Show(
        onSuccessResult: () -> Unit,
        snackBarHostState: SnackbarHostState
    )

    object Initial : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {}
    }

    object Success : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            LaunchedEffect(Unit) {
                onSuccessResult()
            }
        }
    }

    data class Error(private val messageResId: StringResource) : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            val message = stringResource(messageResId)

            LaunchedEffect(messageResId) {
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
            }
        }
    }

    data class Loading(private val message: String = "Загрузка…") : AuthResultUiState {
        @Composable
        override fun Show(
            onSuccessResult: () -> Unit,
            snackBarHostState: SnackbarHostState
        ) {
            LaunchedEffect(message) {
                snackBarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite
                )
            }
        }
    }
}