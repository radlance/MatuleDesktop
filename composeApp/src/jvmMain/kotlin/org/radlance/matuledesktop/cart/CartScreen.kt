package org.radlance.matuledesktop.cart

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import org.radlance.matuledesktop.presentation.common.ProductViewModel

@Composable
internal fun CartScreen(
    imageLoader: ImageLoader,
    viewModel: ProductViewModel
) = Navigator(CartCoreScreen(imageLoader, viewModel)) { navigator ->
    SlideTransition(navigator)
}