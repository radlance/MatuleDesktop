package org.radlance.matuledesktop.presentation.home.search

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.products_not_found
import matuledesktop.composeapp.generated.resources.retry
import matuledesktop.composeapp.generated.resources.search
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.common.ProductGrid
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen

internal class SearchScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val loadContentResult by viewModel.catalogContent.collectAsState()
        val addToFavoriteResult by viewModel.favoriteResult.collectAsState()

        var searchFieldValue by rememberSaveable { mutableStateOf("") }
        var selectedCountryIds by remember { mutableStateOf<List<Int>?>(null) }
        var selectedBrandIds by remember { mutableStateOf<List<Int>?>(null) }
        var selectedSizes by remember { mutableStateOf<List<Int>?>(null) }
        var selectedClaspTypesIds by remember { mutableStateOf<List<Int>?>(null) }
        var selectedMoistureProtectionTypesIds by remember { mutableStateOf<List<Int>?>(null) }
        var selectedColors by remember { mutableStateOf<List<Int>?>(null) }

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


            loadContentResult.Show(
                onSuccess = { fetchContent ->
                    val foundedProducts = fetchContent.products.filter { product ->
                        product.title.contains(searchFieldValue, ignoreCase = true)
                                && selectedCountryIds?.contains(product.originCountryId) ?: true
                                && selectedBrandIds?.contains(product.brandId) ?: true
                                && selectedClaspTypesIds?.contains(product.claspTypeId) ?: true
                                && selectedMoistureProtectionTypesIds?.contains(product.moistureProtectionTypeId) ?: true
                                && selectedSizes?.let { sizes ->
                            product.sizes.any { productSize ->
                                sizes.contains(productSize.size) && productSize.quantity != 0
                            }
                        } ?: true && selectedColors?.let { colors ->
                            product.colors.any { productColor ->
                                colors.contains(productColor.id)
                            }
                        } ?: true
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
                            text = stringResource(Res.string.search),
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 38.sp,
                            modifier = Modifier.padding(end = 15.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    SearchField(
                        value = searchFieldValue,
                        onValueChange = { searchFieldValue = it },
                        onSearchClick = { searchFieldValue = it },
                        hint = stringResource(Res.string.search),
                        fetchContent = fetchContent,
                        onCheckOriginCountry = { selectedCountryIds = it },
                        onCheckClaspType = { selectedClaspTypesIds = it },
                        onCheckBrand = { selectedBrandIds = it },
                        onCheckSize = { selectedSizes = it },
                        onCheckMoistureProtectionType = { selectedMoistureProtectionTypesIds = it },
                        onCheckColor = { selectedColors = it },
                        modifier = Modifier.padding(end = 15.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (foundedProducts.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            val text = if (searchFieldValue.isEmpty()) {
                                stringResource(Res.string.products_not_found)
                            } else {
                                "Товаров по запросу \"$searchFieldValue\" не найдено"
                            }

                            Text(
                                text = text,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 20.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    } else {
                        ProductGrid(
                            imageLoader = imageLoader,
                            products = foundedProducts,
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
                        CircularProgressIndicator()
                    }
                },
                onUnauthorized = {}
            )
        }
    }
}
