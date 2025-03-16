package org.radlance.matuledesktop.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeNavigationRail(
    railTabs: List<NavigationTab>,
    modifier: Modifier = Modifier
) {
    val tabNavigator = LocalTabNavigator.current

    NavigationRail(modifier = modifier) {
        railTabs.forEach { tab ->

            TooltipArea(
                tooltip = {
                    Surface(shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = tab.options.title,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            ) {
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
}