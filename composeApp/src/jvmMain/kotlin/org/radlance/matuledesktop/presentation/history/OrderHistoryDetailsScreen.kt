package org.radlance.matuledesktop.presentation.history

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
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
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.email_full
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.name
import matuledesktop.composeapp.generated.resources.order_data
import matuledesktop.composeapp.generated.resources.order_date
import matuledesktop.composeapp.generated.resources.retry
import matuledesktop.composeapp.generated.resources.total_amount
import matuledesktop.composeapp.generated.resources.total_price
import matuledesktop.composeapp.generated.resources.user_data
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.presentation.cart.CheckoutDataItem
import org.radlance.matuledesktop.presentation.cart.OrderViewModel
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import org.radlance.matuledesktop.presentation.common.TimeFormat
import java.text.NumberFormat
import java.time.ZoneId
import java.util.Locale

class OrderHistoryDetailsScreen(
    private val viewModel: ProductViewModel,
    private val selectedOrderId: Int
) : Screen, TimeFormat() {

    @Composable
    override fun Content() {
        val orderViewModel = koinViewModel<OrderViewModel>()
        val navigator = LocalNavigator.currentOrThrow

        val numberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))
        val zoneId = ZoneId.systemDefault()
        val scrollState = rememberScrollState()
        var currentUser by remember { mutableStateOf(User()) }

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val loadHistoryResulUiState by viewModel.loadHistoryResultUiState.collectAsState()
        val userUiState by orderViewModel.userUiState.collectAsState()

        userUiState.Show(
            onSuccess = { userData ->
                currentUser = userData
            },
            onError = {},
            onLoading = {},
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
                    text = "Заказ #${selectedOrderId}",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    modifier = Modifier.padding(end = 15.dp)
                )

                Spacer(Modifier.height(10.dp))
            }

            loadContentResult.Show(
                onSuccess = { fetchContent ->
                    loadHistoryResulUiState.Show(
                        onSuccess = { orders ->
                            val currentOrder = orders.first { it.id == selectedOrderId }

                            Text(
                                text = stringResource(Res.string.user_data),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 19.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )

                            Column(modifier = Modifier.fillMaxWidth().padding(end = 15.dp)) {
                                Spacer(Modifier.height(10.dp))
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    CheckoutDataItem(
                                        label = stringResource(Res.string.name),
                                        value = currentUser.firstName
                                    )

                                    CheckoutDataItem(
                                        label = stringResource(Res.string.email_full),
                                        value = currentUser.email
                                    )
                                }
                            }

                            Spacer(Modifier.height(40.dp))

                            Text(
                                text = stringResource(Res.string.order_data),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 19.sp,
                                modifier = Modifier.align(Alignment.Start)
                            )

                            Column(modifier = Modifier.fillMaxWidth()) {
                                Spacer(Modifier.height(10.dp))
                                Box {
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(10.dp),
                                        modifier = Modifier
                                            .verticalScroll(scrollState)
                                            .padding(end = 15.dp)
                                    ) {
                                        val formattedDate = formatUtcTimestampToLocal(
                                            timestamp = currentOrder.date,
                                            zoneId = zoneId
                                        )

                                        CheckoutDataItem(
                                            label = stringResource(Res.string.order_date),
                                            value = formattedDate
                                        )

                                        CheckoutDataItem(
                                            label = stringResource(Res.string.total_price),
                                            value = "${numberFormat.format(currentOrder.totalPrice)} ₽"
                                        )

                                        CheckoutDataItem(
                                            label = stringResource(Res.string.total_amount),
                                            value = currentOrder.orderItems.sumOf {
                                                it.quantity
                                            }.toString()
                                        )

                                        currentOrder.orderItems.forEach { orderItem ->
                                            val product = fetchContent.products.first {
                                                it.id == orderItem.productId
                                            }

                                            CheckoutDataItem(
                                                label = "${product.title}, ${orderItem.size} размер",
                                                value = "${numberFormat.format(product.price)} ₽ x${orderItem.quantity}"
                                            )
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