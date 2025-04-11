package org.radlance.matuledesktop.cart

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import matuledesktop.composeapp.generated.resources.cart
import matuledesktop.composeapp.generated.resources.cart_is_empty
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.proceed_to_checkout
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen
import java.text.NumberFormat
import java.util.Locale

class CartCoreScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val loadCartResult by viewModel.cartContent.collectAsState()

        val verticalScrollState = rememberScrollState()
        val numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(Res.string.cart),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            loadContentResult.Show(
                onSuccess = { fetchContent ->
                    loadCartResult.Show(
                        onSuccess = { cartItems ->
                            val items = cartItems.filter { it.quantity > 0 }

                            if (cartItems.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(end = 15.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = stringResource(Res.string.cart_is_empty))
                                }
                            } else {
                                Row(modifier = Modifier.align(Alignment.Start)) {
                                    Text(
                                        text = "Товары, ${items.sumOf { it.quantity }} шт.",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 19.sp
                                    )

                                    VerticalDivider(
                                        modifier = Modifier
                                            .height(20.dp)
                                            .padding(horizontal = 8.dp)
                                    )

                                    val totalPrice = items.sumOf { item ->
                                        item.quantity * fetchContent.products.first {
                                            it.id == item.productId
                                        }.price
                                    }

                                    Text(
                                        text = "Итого: ${numberFormat.format(totalPrice)} ₽",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        lineHeight = 19.sp
                                    )
                                }

                                Spacer(Modifier.height(10.dp))

                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .verticalScroll(verticalScrollState)
                                            .padding(end = 15.dp)
                                    ) {
                                        items.forEach { product ->
                                            CartItemCard(
                                                imageLoader = imageLoader,
                                                product = fetchContent.products.first { it.id == product.productId },
                                                size = product.productSize,
                                                quantity = product.quantity,
                                                numberFormat = numberFormat,
                                                modifier = Modifier.clickable {
                                                    navigator.push(
                                                        ProductDetailsScreen(
                                                            selectedProductId = product.productId,
                                                            imageLoader = imageLoader,
                                                            viewModel = viewModel
                                                        )
                                                    )
                                                }

                                            )
                                        }

                                        Spacer(Modifier.height(40.dp))
                                    }

                                    Column(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(end = 15.dp)
                                            .align(Alignment.BottomCenter)
                                    ) {
                                        Button(
                                            onClick = {},
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Text(stringResource(Res.string.proceed_to_checkout))
                                        }

                                    }

                                    Spacer(Modifier.height(10.dp))
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
                                    Button(onClick = viewModel::fetchCartItems) {
                                        Text(stringResource(Res.string.retry), color = Color.White)
                                    }
                                }
                            }
                        },
                        onUnauthorized = {}
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