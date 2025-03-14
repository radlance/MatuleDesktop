package org.radlance.matuledesktop.presentation.core

import androidx.compose.runtime.Composable
import org.radlance.matuledesktop.presentation.auth.signin.SignInScreen
import org.radlance.matuledesktop.presentation.auth.signup.SignUpScreen

interface Screen {

    @Composable
    fun Show(navigate: (Screen) -> Unit)

    object SignIn : Screen {

        @Composable
        override fun Show(navigate: (Screen) -> Unit) {
            SignInScreen(navigateToSignUp = { navigate(SignUp) })
        }
    }

    object SignUp : Screen {

        @Composable
        override fun Show(navigate: (Screen) -> Unit) {
            SignUpScreen(navigateToSignInScreen = { navigate(SignIn) })
        }
    }
}