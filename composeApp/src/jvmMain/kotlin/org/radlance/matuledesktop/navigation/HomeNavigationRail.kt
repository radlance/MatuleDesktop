package org.radlance.matuledesktop.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

@Composable
fun HomeNavigationRail(
    railTabs: List<NavigationTab>,
    modifier: Modifier = Modifier
) {
    val tabNavigator = LocalTabNavigator.current

    NavigationRail(modifier = modifier) {
        railTabs.forEach { tab ->
            NavigationRailItem(
                selected = tab == tabNavigator.current,
                onClick = { tabNavigator.current = tab },
                icon = {
                    Icon(
                        imageVector = tab.icon(),
                        contentDescription = tab.options.title
                    )
                }
            )
        }
    }
}