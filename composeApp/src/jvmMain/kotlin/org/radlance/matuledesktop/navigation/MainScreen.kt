package org.radlance.matuledesktop.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import org.koin.compose.viewmodel.koinViewModel
import org.radlance.matuledesktop.presentation.common.ProductViewModel

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = koinViewModel<ProductViewModel>()
        val imageLoader = remember {
            ImageLoader(PlatformContext.INSTANCE).newBuilder()
                .crossfade(true)
                .build()
        }

        val railTabs = listOf(
            NavigationTab.Home(imageLoader, viewModel),
            NavigationTab.Favorite(imageLoader, viewModel)
        )

        TabNavigator(railTabs.first()) {
            Row(Modifier.fillMaxSize()) {

                HomeNavigationRail(railTabs = railTabs)

                Box(Modifier.weight(1f)) { CurrentTab() }
            }
        }
    }
}