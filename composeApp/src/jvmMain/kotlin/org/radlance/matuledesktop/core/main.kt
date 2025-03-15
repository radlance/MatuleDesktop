package org.radlance.matuledesktop.core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.radlance.matuledesktop.di.initCoin
import org.radlance.matuledesktop.theme.AppTheme
import java.awt.Dimension

fun main() = application {
    initCoin()
    var currentScreen by remember { mutableStateOf<Screen>(Screen.SignIn) }

    Window(
        title = "MatuleDesktop",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)

        AppTheme {
            currentScreen.Show(navigate = { currentScreen = it })
        }
    }
}