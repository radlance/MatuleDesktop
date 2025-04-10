package org.radlance.matuledesktop.presentation.home.details

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.add_to_cart
import matuledesktop.composeapp.generated.resources.add_to_favorite
import matuledesktop.composeapp.generated.resources.in_cart
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.navigation.NavigationTab
import org.radlance.matuledesktop.presentation.common.ChangeProductStatus
import org.radlance.matuledesktop.presentation.common.ProductCardImage
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import java.text.NumberFormat
import java.util.Locale

internal class ProductDetailsScreen(
    private val selectedProductId: Int,
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val tabNavigator = LocalTabNavigator.current
        val navigator = LocalNavigator.currentOrThrow
        val containerSize = LocalWindowInfo.current.containerSize

        val numberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))
        val scrollState = rememberScrollState()
        var selectedProductSize by rememberSaveable { mutableStateOf(-1) }

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val addToFavoriteResult by viewModel.favoriteResult.collectAsState()
        val addToCartResult by viewModel.inCartResult.collectAsState()

        var addedInCartCurrent by rememberSaveable { mutableStateOf(false) }

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
            onLoading = { addedInCartCurrent = true },
            onError = { addedInCartCurrent = false },
            onUnauthorized = {}
        )

        loadContentResult.Show(
            onSuccess = { fetchContent ->
                val selectedProduct = fetchContent.products.first { it.id == selectedProductId }
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
                            text = selectedProduct.modelName,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 38.sp,
                            modifier = Modifier.padding(end = 15.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box {
                        Column(modifier = Modifier.verticalScroll(scrollState).fillMaxWidth()) {
                            Row(modifier = Modifier.align(Alignment.Start)) {
                                Box(modifier = Modifier.weight(1.3f)) {
                                    ProductCardImage(
                                        imageLoader,
                                        imageUrl = selectedProduct.imageUrl,
                                        modifier = Modifier.heightIn(max = (containerSize.height).dp)
                                    )
                                }

                                Spacer(Modifier.weight(0.2f))
                                Column(modifier = Modifier.weight(1f).padding(end = 35.dp)) {
                                    ProductDetailsTitle(fetchContent, selectedProduct)
                                    Spacer(Modifier.height(20.dp))

                                    Text(
                                        text = "${numberFormat.format(selectedProduct.price)} ₽",
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 46.sp,
                                    )

                                    Spacer(Modifier.height(20.dp))
                                    ProductDetailsProperties(fetchContent, selectedProduct)
                                    Spacer(Modifier.height(20.dp))

                                    ProductDetailsSizeSection(
                                        selectedProduct,
                                        selectedProductSize,
                                        onSizeClick = { selectedProductSize = it }
                                    )

                                    Spacer(Modifier.height(20.dp))
                                    Row(modifier = Modifier.fillMaxWidth()) {

                                        OutlinedIconButton(
                                            onClick = {
                                                viewModel.changeFavoriteStatus(selectedProduct.id)
                                            },
                                            modifier = Modifier.size(52.dp)
                                        ) {
                                            if (selectedProduct.isFavorite) {
                                                Icon(
                                                    imageVector = Icons.Default.Favorite,
                                                    tint = Color.Red,
                                                    contentDescription = stringResource(Res.string.add_to_favorite)
                                                )
                                            } else {
                                                Icon(
                                                    imageVector = Icons.Default.FavoriteBorder,
                                                    contentDescription = stringResource(Res.string.add_to_favorite)
                                                )
                                            }
                                        }

                                        Spacer(Modifier.width(20.dp))

                                        OutlinedButton(
                                            onClick = {
                                                if (addedInCartCurrent) {
                                                    tabNavigator.current =
                                                        NavigationTab.Cart(imageLoader, viewModel)
                                                } else {
                                                    viewModel.addProductToCart(
                                                        selectedProductId,
                                                        selectedProduct.sizes.first { it.size == selectedProductSize }.sizeId
                                                    )
                                                }
                                            },
                                            enabled = selectedProductSize != -1,
                                            modifier = Modifier.weight(1f).height(52.dp)
                                        ) {
                                            val buttonText = if (addedInCartCurrent) {
                                                Res.string.in_cart
                                            } else {
                                                Res.string.add_to_cart
                                            }
                                            Text(
                                                text = stringResource(buttonText)
                                            )
                                        }
                                    }

                                    Spacer(Modifier.height(20.dp))
                                }
                            }
                        }

                        VerticalScrollbar(
                            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                            adapter = rememberScrollbarAdapter(scrollState)
                        )
                    }
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