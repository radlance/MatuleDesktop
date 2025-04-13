package org.radlance.matuledesktop.presentation.home.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import org.radlance.matuledesktop.presentation.common.ProductViewModel


@Composable
internal fun HomeScreen(
    imageLoader: ImageLoader,
    viewModel: ProductViewModel,
    resetNavigation: Boolean = false
) {
    var internalResetFlag by remember { mutableStateOf(resetNavigation) }

    Navigator(HomeCoreScreen(imageLoader, viewModel)) { navigator ->
        LaunchedEffect(internalResetFlag) {
            if (internalResetFlag) {
                navigator.popUntil { it is HomeCoreScreen }
                internalResetFlag = false
            }
        }
        SlideTransition(navigator)
    }
}