package org.radlance.matuledesktop.presentation.favorite

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import org.radlance.matuledesktop.presentation.common.ProductViewModel

@Composable
fun FavoriteScreen(
    imageLoader: ImageLoader,
    viewModel: ProductViewModel
) = Navigator(FavoriteCoreScreen(imageLoader, viewModel)) { navigator ->
    SlideTransition(navigator)
}