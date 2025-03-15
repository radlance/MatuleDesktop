package org.radlance.matuledesktop.core

import androidx.compose.runtime.Composable
import org.radlance.matuledesktop.presentation.auth.signin.SignInScreen
import org.radlance.matuledesktop.presentation.auth.signup.SignUpScreen
import org.radlance.matuledesktop.presentation.home.HomeScreen

interface Screen {

    @Composable
    fun Show(navigate: (Screen) -> Unit)

    object SignIn : Screen {

        @Composable
        override fun Show(navigate: (Screen) -> Unit) {
            SignInScreen(
                navigateToSignUp = { navigate(SignUp) },
                navigateToHomeScreen = { navigate(Home) }
            )
        }
    }

    object SignUp : Screen {

        @Composable
        override fun Show(navigate: (Screen) -> Unit) {
            SignUpScreen(navigateToSignInScreen = { navigate(SignIn) })
        }
    }

    object Home : Screen {

        @Composable
        override fun Show(navigate: (Screen) -> Unit) {
            HomeScreen()
        }
    }
}