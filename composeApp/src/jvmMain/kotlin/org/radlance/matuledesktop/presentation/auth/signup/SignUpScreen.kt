package org.radlance.matuledesktop.presentation.auth.signup

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
import matuledesktop.composeapp.generated.resources.already_have_an_account
import matuledesktop.composeapp.generated.resources.email
import matuledesktop.composeapp.generated.resources.email_hint
import matuledesktop.composeapp.generated.resources.fill_your_data
import matuledesktop.composeapp.generated.resources.name_hint
import matuledesktop.composeapp.generated.resources.password
import matuledesktop.composeapp.generated.resources.password_hint
import matuledesktop.composeapp.generated.resources.registration
import matuledesktop.composeapp.generated.resources.sign_in
import matuledesktop.composeapp.generated.resources.sign_up
import matuledesktop.composeapp.generated.resources.your_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.radlance.matuledesktop.presentation.auth.common.PasswordState
import org.radlance.matuledesktop.presentation.common.AuthScaffold
import org.radlance.matuledesktop.presentation.home.HomeScreen

class SignUpScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val viewModel = koinViewModel<SignUpViewModel>()

        var passwordState by remember { mutableStateOf<PasswordState>(PasswordState.Invisible) }

        val authUiState by viewModel.authUiState.collectAsState()
        val signUpResultUiState by viewModel.signUpResultUiState.collectAsState()
        val snackBarHostState = remember { SnackbarHostState() }

        signUpResultUiState.Show(
            onSuccessResult = { navigator.push(HomeScreen()) },
            snackBarHostState = snackBarHostState
        )

        AuthScaffold(snackBarHostState) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                var nameFieldValue by remember { mutableStateOf("") }
                var emailFieldValue by remember { mutableStateOf("") }
                var passwordFieldValue by remember { mutableStateOf("") }

                Text(
                    text = stringResource(Res.string.registration),
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
                    value = nameFieldValue,
                    onValueChange = {
                        nameFieldValue = it
                        viewModel.resetNameError()
                    },
                    label = { Text(text = stringResource(Res.string.your_name)) },
                    placeholder = { Text(text = stringResource(Res.string.name_hint)) },
                    singleLine = true,
                    isError = !authUiState.isCorrectName,
                    modifier = Modifier.width(300.dp)
                )

                OutlinedTextField(
                    value = emailFieldValue,
                    onValueChange = {
                        emailFieldValue = it
                        viewModel.resetEmailError()
                    },
                    label = { Text(text = stringResource(Res.string.email)) },
                    placeholder = { Text(text = stringResource(Res.string.email_hint)) },
                    singleLine = true,
                    isError = !authUiState.isCorrectEmail,
                    modifier = Modifier.width(300.dp)
                )

                OutlinedTextField(
                    value = passwordFieldValue,
                    onValueChange = {
                        passwordFieldValue = it
                        viewModel.resetPasswordError()
                    },
                    singleLine = true,
                    isError = !authUiState.isCorrectPassword,
                    label = { Text(text = stringResource(Res.string.password)) },
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
                    modifier = Modifier.width(300.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        viewModel.signUp(
                            name = nameFieldValue,
                            email = emailFieldValue,
                            password = passwordFieldValue
                        )
                    }
                ) {
                    Text(text = stringResource(Res.string.sign_up))
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text(
                        text = stringResource(Res.string.already_have_an_account),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 1.sp
                    )

                    Text(
                        text = stringResource(Res.string.sign_in),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 1.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable { navigator.pop() }
                    )
                }
            }
        }
    }

}