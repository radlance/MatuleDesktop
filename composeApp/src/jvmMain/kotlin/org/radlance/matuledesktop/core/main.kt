package org.radlance.matuledesktop.core

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.radlance.matuledesktop.di.initCoin
import org.radlance.matuledesktop.presentation.auth.signin.SignInScreen
import org.radlance.matuledesktop.theme.AppTheme
import java.awt.Dimension

fun main() = application {
    initCoin()

    Window(
        title = "MatuleDesktop",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)

        AppTheme {
            Navigator(SignInScreen) { navigator ->
                SlideTransition(navigator)
            }
        }
    }
}