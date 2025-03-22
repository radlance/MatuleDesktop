package org.radlance.matuledesktop.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
internal fun ChangeProductStatus(
    productId: Int?,
    onStatusChanged: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        productId?.let { onStatusChanged(productId) }
    }
}
