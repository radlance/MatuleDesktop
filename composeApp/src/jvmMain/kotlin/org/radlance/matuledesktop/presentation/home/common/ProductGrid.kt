package org.radlance.matuledesktop.presentation.home.common

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
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.core.ProductCard

@Composable
fun ProductGrid(
    products: List<Product>,
    viewModel: ProductViewModel,
    navigateToCart: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val lazyGridState = rememberLazyGridState()

    Box {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = modifier.fillMaxSize().padding(end = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            verticalArrangement = Arrangement.spacedBy(13.dp)
        ) {
            items(items = products, key = { product -> product.id }) { product ->
                ProductCard(
                    product = product,
                    onLikeClick = { viewModel.changeFavoriteStatus(product.id) },
                    onCartClick = {
                        if (product.quantityInCart == 0) {
                            viewModel.addProductToCart(product.id)
                        } else {
                            navigateToCart()
                        }
                    },
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