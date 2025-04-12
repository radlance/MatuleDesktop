package org.radlance.matuledesktop.presentation.common

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import org.radlance.matuledesktop.domain.product.Product

@Composable
internal fun ProductGrid(
    imageLoader: ImageLoader,
    products: List<Product>,
    viewModel: ProductViewModel,
    navigateToDetails: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()

    Box {
        LazyVerticalGrid(
            state = lazyGridState,
            columns = GridCells.Fixed(3),
            modifier = modifier.fillMaxSize().padding(end = 15.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(items = products, key = { product -> product.id }) { product ->
                ProductCard(
                    imageLoader = imageLoader,
                    product = product,
                    onLikeClick = { viewModel.changeFavoriteStatus(product.id) },
                    onCardClick = navigateToDetails
                )
            }
        }

        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(lazyGridState)
        )
    }
}