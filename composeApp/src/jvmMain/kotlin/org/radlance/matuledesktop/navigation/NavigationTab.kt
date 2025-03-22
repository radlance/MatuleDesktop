package org.radlance.matuledesktop.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.favorite
import matuledesktop.composeapp.generated.resources.home_screen
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.favorite.FavoriteScreen
import org.radlance.matuledesktop.presentation.home.core.HomeScreen

interface NavigationTab : Tab {

    fun icon(): ImageVector


    data class Home(
        private val imageLoader: ImageLoader,
        private val viewModel: ProductViewModel
    ) : NavigationTab {

        override fun icon(): ImageVector = Icons.Default.Home

        override val options: TabOptions
            @Composable
            get() = TabOptions(index = 0u, title = stringResource(Res.string.home_screen))

        @Composable
        override fun Content() {
            HomeScreen(imageLoader, viewModel)
        }
    }

    data class Favorite(
        private val imageLoader: ImageLoader,
        private val viewModel: ProductViewModel
    ) : NavigationTab {

        override fun icon(): ImageVector = Icons.Default.Favorite

        override val options: TabOptions
            @Composable
            get() = TabOptions(index = 1u, title = stringResource(Res.string.favorite))

        @Composable
        override fun Content() {
            FavoriteScreen(imageLoader, viewModel)
        }
    }
}
