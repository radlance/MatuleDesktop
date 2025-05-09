package org.radlance.matuledesktop.presentation.home.catalog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import matuledesktop.composeapp.generated.resources.all
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.common.CategoriesRow
import org.radlance.matuledesktop.presentation.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.common.ProductGrid
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen

internal class CatalogScreen(
    private val selectedCategoryId: Int?,
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val fetchResult by viewModel.catalogContent.collectAsState()
        val addToFavoriteResult by viewModel.favoriteResult.collectAsState()

        var selectedCategoryIdState by rememberSaveable { mutableStateOf(selectedCategoryId) }

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
            Spacer(modifier = Modifier.height(10.dp))

            fetchResult.Show(
                onSuccess = { fetchContent ->
                    val selectedCategory = selectedCategoryIdState?.let {
                        fetchContent.categories.first { it.id == selectedCategoryIdState }
                    }

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
                            text = selectedCategory?.title ?: stringResource(Res.string.all),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 38.sp,
                            modifier = Modifier.padding(end = 15.dp)
                        )
                    }

                    val product = selectedCategory?.let {
                        fetchContent.products.filter { it.categoryId == selectedCategory.id }
                    } ?: fetchContent.products

                    CategoriesRow(
                        categories = fetchContent.categories,
                        onCategoryClick = { selectedCategoryIdState = it },
                        selectedCategoryId = selectedCategoryIdState ?: 0
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    ProductGrid(
                        products = product,
                        viewModel = viewModel,
                        imageLoader = imageLoader,
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

                onLoading = {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.offset(y = (-55).dp))
                    }
                },

                onUnauthorized = {}
            )
        }
    }
}