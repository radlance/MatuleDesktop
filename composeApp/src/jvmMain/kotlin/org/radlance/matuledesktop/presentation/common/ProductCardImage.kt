package org.radlance.matuledesktop.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.size.Size

@Composable
fun ProductCardImage(
    imageLoader: ImageLoader,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(PlatformContext.INSTANCE).size(Size.ORIGINAL)
            .data(imageUrl)
            .build(),
        imageLoader = imageLoader,
        contentDescription = "shoe",
        contentScale = ContentScale.FillWidth,
        loading = {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },

        modifier = modifier
    )
}