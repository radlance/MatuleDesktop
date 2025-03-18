package org.radlance.matuledesktop.presentation.home.core

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import org.radlance.matuledesktop.presentation.common.ProductViewModel


@Composable
fun HomeScreen(
    imageLoader: ImageLoader,
    viewModel: ProductViewModel
) = Navigator(HomeCoreScreen(imageLoader, viewModel)) { navigator ->
    SlideTransition(navigator)
}
