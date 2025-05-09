package org.radlance.matuledesktop.presentation.auth.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.email
import matuledesktop.composeapp.generated.resources.email_hint
import matuledesktop.composeapp.generated.resources.fill_your_data
import matuledesktop.composeapp.generated.resources.hello
import matuledesktop.composeapp.generated.resources.is_first_time
import matuledesktop.composeapp.generated.resources.password
import matuledesktop.composeapp.generated.resources.password_hint
import matuledesktop.composeapp.generated.resources.sign_in
import matuledesktop.composeapp.generated.resources.sign_up
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.radlance.matuledesktop.navigation.MainScreen
import org.radlance.matuledesktop.presentation.auth.common.PasswordState
import org.radlance.matuledesktop.presentation.auth.signup.SignUpScreen
import org.radlance.matuledesktop.presentation.common.AuthScaffold

internal object SignInScreen : Screen {
    private fun readResolve(): Any = SignInScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = koinViewModel<SignInViewModel>()

        var passwordState by remember { mutableStateOf<PasswordState>(PasswordState.Invisible) }

        val authUiState by viewModel.authUiState.collectAsState()
        val signInResultUiState by viewModel.signInResultUiState.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }

        signInResultUiState.Show(
            onSuccessResult = { navigator.push(MainScreen()) },
            snackBarHostState = snackBarHostState
        )

        AuthScaffold(snackBarHostState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var emailFieldValue by remember { mutableStateOf("") }

                var passwordFieldValue by remember { mutableStateOf("") }

                Text(
                    text = stringResource(Res.string.hello),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    modifier = Modifier.padding(top = 11.dp)
                )

                Text(
                    text = stringResource(Res.string.fill_your_data),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = emailFieldValue,
                    onValueChange = {
                        emailFieldValue = it
                        viewModel.resetEmailError()
                    },
                    label = {
                        val text =
                            if (authUiState.emailErrorMessage.isEmpty() || emailFieldValue.isEmpty()) {
                                stringResource(Res.string.email)
                            } else {
                                authUiState.emailErrorMessage
                            }
                        Text(text = text)
                    },
                    placeholder = { Text(text = stringResource(Res.string.email_hint)) },
                    singleLine = true,
                    isError = authUiState.emailErrorMessage.isNotEmpty(),
                    modifier = Modifier.width(300.dp)
                )

                OutlinedTextField(
                    value = passwordFieldValue,
                    onValueChange = {
                        passwordFieldValue = it
                        viewModel.resetPasswordError()
                    },
                    singleLine = true,
                    isError = authUiState.passwordErrorMessage.isNotEmpty(),
                    label = {
                        val text =
                            if (authUiState.passwordErrorMessage.isEmpty() || passwordFieldValue.isEmpty()) {
                                stringResource(Res.string.password)
                            } else {
                                authUiState.passwordErrorMessage
                            }
                        Text(text = text)
                    },
                    placeholder = { Text(text = stringResource(Res.string.password_hint)) },
                    visualTransformation = passwordState.visualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordState = passwordState.inverse() }) {
                            Icon(
                                imageVector = passwordState.icon(),
                                contentDescription = stringResource(passwordState.contentDescriptionId())
                            )
                        }
                    },
                    modifier = Modifier.width(300.dp),
                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { viewModel.signIn(emailFieldValue, passwordFieldValue) }
                ) {
                    Text(text = stringResource(Res.string.sign_in))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text(
                        text = stringResource(Res.string.is_first_time),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 1.sp
                    )

                    Text(
                        text = stringResource(Res.string.sign_up),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 1.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { navigator.push(SignUpScreen) }
                    )
                }
            }
        }
    }
}