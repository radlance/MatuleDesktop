package org.radlance.matuledesktop.presentation.history

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.history_is_empty
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.order_history
import matuledesktop.composeapp.generated.resources.products
import matuledesktop.composeapp.generated.resources.retry
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.common.TimeFormat
import org.radlance.matuledesktop.presentation.home.details.ProductDetailsScreen
import java.text.NumberFormat
import java.time.ZoneId
import java.util.Locale

class OrderHistoryCoreScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen, TimeFormat() {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val numberFormat = NumberFormat.getNumberInstance(Locale("ru"))

        val loadHistoryResulUiState by viewModel.loadHistoryResultUiState.collectAsState()
        val fetchContentResultUiState by viewModel.catalogContent.collectAsState()

        val zoneId = ZoneId.systemDefault()

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.order_history),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            fetchContentResultUiState.Show(
                onSuccess = { fetchContent ->
                    loadHistoryResulUiState.Show(
                        onSuccess = { orderHistory ->
                            if (orderHistory.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize().padding(end = 15.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = stringResource(Res.string.history_is_empty))
                                }
                            } else {
                                Box {
                                    Column(
                                        modifier = Modifier.verticalScroll(scrollState)
                                            .padding(end = 15.dp),
                                        verticalArrangement = Arrangement.spacedBy(36.dp)
                                    ) {
                                        orderHistory.forEach { order ->
                                            val formattedDate = formatUtcTimestampToLocal(
                                                timestamp = order.date,
                                                zoneId = zoneId
                                            )

                                            Column {
                                                Text(
                                                    text = buildAnnotatedString {
                                                        withStyle(SpanStyle()) {
                                                            append("Заказ ")
                                                        }

                                                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                                            append("#${order.id}")
                                                        }

                                                        withStyle(SpanStyle()) {
                                                            append(", $formattedDate")
                                                        }
                                                    },
                                                    fontSize = 16.sp,
                                                    fontWeight = FontWeight.SemiBold,
                                                    lineHeight = 19.sp,
                                                    modifier = Modifier.clickable {
                                                        navigator.push(
                                                            OrderHistoryDetailsScreen(
                                                                viewModel = viewModel,
                                                                selectedOrderId = order.id
                                                            )
                                                        )
                                                    }
                                                )

                                                Spacer(Modifier.height(4.dp))

                                                OrderHistoryItem(
                                                    order = order,
                                                    products = fetchContent.products,
                                                    imageLoader = imageLoader,
                                                    onProductClick = { productId ->
                                                        navigator.push(
                                                            ProductDetailsScreen(
                                                                selectedProductId = productId,
                                                                imageLoader = imageLoader,
                                                                viewModel = viewModel
                                                            )
                                                        )
                                                    }
                                                )

                                                Spacer(Modifier.height(4.dp))

                                                val quantity =
                                                    order.orderItems.sumOf { it.quantity }
                                                val productPluralString = pluralStringResource(
                                                    Res.plurals.products,
                                                    quantity,
                                                    quantity
                                                )

                                                val totalPrice =
                                                    numberFormat.format(order.totalPrice)

                                                Text(
                                                    text = "$productPluralString, $totalPrice ₽",
                                                    fontSize = 14.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    lineHeight = 20.sp
                                                )
                                            }
                                        }

                                        Spacer(Modifier.height(10.dp))
                                    }


                                    VerticalScrollbar(
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                            .fillMaxHeight(),
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
                                    Button(onClick = viewModel::fetchOrderHistory) {
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

