package org.radlance.matuledesktop.presentation.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.favorite
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.no_favorite_products
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.common.ProductGrid
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen

internal class FavoriteCoreScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val addToFavoriteResult by viewModel.favoriteResult.collectAsState()

        addToFavoriteResult.Show(
            onSuccess = {},
            onLoading = { productId ->
                ChangeProductStatus(productId, viewModel::changeStateFavoriteStatus)
            },
            onError = { productId ->
                ChangeProductStatus(productId, viewModel::changeStateFavoriteStatus)
            },
            onUnauthorized = {}
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(Res.string.favorite),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            loadContentResult.Show(
                onSuccess = { fetchContent ->

                    val favoriteProducts = fetchContent.products.filter { it.isFavorite }

                    if (favoriteProducts.isEmpty()) {
                        Box(
                            Modifier.fillMaxSize().padding(end = 15.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(Res.string.no_favorite_products))
                        }
                    } else {
                        ProductGrid(
                            imageLoader = imageLoader,
                            products = favoriteProducts,
                            viewModel = viewModel,
                            navigateToDetails = {
                                navigator.push(
                                    ProductDetailsScreen(
                                        selectedProductId = it.id,
                                        imageLoader = imageLoader,
                                        viewModel = viewModel
                                    )
                                )
                            }
                        )
                    }
                },

                onLoading = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
                onError = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = stringResource(Res.string.load_error))
                            Spacer(Modifier.height(12.dp))
                            Button(onClick = viewModel::fetchContent) {
                                Text(stringResource(Res.string.retry), color = Color.White)
                            }
                        }
                    }
                },
                onUnauthorized = {}
            )
        }
    }
}