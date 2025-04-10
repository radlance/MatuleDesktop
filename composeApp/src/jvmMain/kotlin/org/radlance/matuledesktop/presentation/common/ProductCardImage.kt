package org.radlance.matuledesktop.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.size.Size

@Composable
internal fun ProductCardImage(
    imageLoader: ImageLoader,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest.Builder(PlatformContext.INSTANCE).size(Size.ORIGINAL)
            .data(imageUrl)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "shoe",
        contentScale = ContentScale.FillWidth,
        modifier = modifier
    )
}