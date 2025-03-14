package org.radlance.matuledesktop.presentation.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.radlance.matuledesktop.presentation.auth.signin.SignInScreen
import org.radlance.matuledesktop.presentation.auth.signup.SignUpScreen
import java.awt.Dimension

fun main() = application {
    var currentScreen by remember { mutableStateOf(Screen.SignIn) }

    Window(
        title = "MatuleDesktop",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)

        when (currentScreen) {
            Screen.SignIn -> SignInScreen(
                navigateToSignUp = { currentScreen = Screen.SignUp }
            )

            Screen.SignUp -> SignUpScreen(
                navigateToSignInScreen = { currentScreen = Screen.SignIn }
            )
        }
    }
}