package org.radlance.matuledesktop.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val railTabs = listOf(NavigationTab.Home, NavigationTab.Favorite)

        TabNavigator(NavigationTab.Home) {
            Row(Modifier.fillMaxSize()) {

                HomeNavigationRail(railTabs = railTabs)

                Box(Modifier.weight(1f)) { CurrentTab() }
            }
        }
    }
}