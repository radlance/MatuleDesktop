package org.radlance.matuledesktop.history

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import org.radlance.matuledesktop.presentation.common.ProductViewModel

@Composable
fun OrderHistoryScreen(
    imageLoader: ImageLoader,
    viewModel: ProductViewModel
) = Navigator(OrderHistoryCoreScreen(imageLoader, viewModel)) { navigator ->
    SlideTransition(navigator)
}