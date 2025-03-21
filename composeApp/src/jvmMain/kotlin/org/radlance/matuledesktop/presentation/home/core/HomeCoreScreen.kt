package org.radlance.matuledesktop.presentation.home.core

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
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
import matuledesktop.composeapp.generated.resources.home_screen
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.retry
import matuledesktop.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.catalog.CatalogScreen
import org.radlance.matuledesktop.presentation.home.common.CategoriesRow
import org.radlance.matuledesktop.presentation.home.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.home.popular.PopularScreen
import org.radlance.matuledesktop.presentation.home.search.SearchScreen

internal class HomeCoreScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val verticalScrollState = rememberScrollState()

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

            Text(
                text = stringResource(Res.string.home_screen),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            HomeSearchBar(
                hint = stringResource(Res.string.search),
                onSearchFieldClick = { navigator.push(SearchScreen(imageLoader, viewModel)) },
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box {
                loadContentResult.Show(
                    onSuccess = {
                        Column(
                            modifier = Modifier.verticalScroll(verticalScrollState)
                        ) {
                            CategoriesRow(
                                categories = it.categories,
                                onCategoryClick = {
                                    navigator.push(
                                        CatalogScreen(
                                            selectedCategoryId = it,
                                            imageLoader = imageLoader,
                                            viewModel = viewModel
                                        )
                                    )
                                },
                                selectedCategoryId = -1
                            )

                            Spacer(Modifier.height(24.dp))

                            PopularRow(
                                imageLoader = imageLoader,
                                products = it.products,
                                onLikeClick = viewModel::changeFavoriteStatus,
                                onAddCartClick = viewModel::addProductToCart,
                                onCardClick = {},
                                navigateToCart = {

                                },
                                navigateToPopular = {
                                    navigator.push(PopularScreen(viewModel, imageLoader))
                                },
                                modifier = Modifier.padding(end = 15.dp)
                            )

                            Spacer(modifier = Modifier.height(20.dp))
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


                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(verticalScrollState)
                )
            }
        }
    }

}