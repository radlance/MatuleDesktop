package org.radlance.matuledesktop.presentation.home.popular

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.popular
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.common.ProductGrid
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen

internal class PopularScreen(
    private val viewModel: ProductViewModel,
    private val imageLoader: ImageLoader
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val addToFavoriteResult by viewModel.favoriteResult.collectAsState()
        val addToCartResult by viewModel.inCartResult.collectAsState()

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

        addToCartResult.Show(
            onSuccess = {},
            onLoading = { productId ->
                ChangeProductStatus(productId, viewModel::changeStateInCartStatus)
            },
            onError = { productId ->
                ChangeProductStatus(
                    productId = productId,
                    onStatusChanged = { viewModel.changeStateInCartStatus(it, recover = true) }
                )
            },
            onUnauthorized = {}
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                IconButton(
                    onClick = navigator::pop,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }

                Text(
                    text = stringResource(Res.string.popular),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    modifier = Modifier.padding(end = 15.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            loadContentResult.Show(
                onSuccess = { fetchContent ->

                    val popularProducts = fetchContent.products.filter { it.isPopular }

                    ProductGrid(
                        imageLoader = imageLoader,
                        products = popularProducts,
                        viewModel = viewModel,
                        navigateToCart = {},
                        navigateToDetails = {
                            navigator.push(
                                ProductDetailsScreen(
                                    selectedProduct = it,
                                    imageLoader = imageLoader,
                                    viewModel = viewModel
                                )
                            )
                        }
                    )
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