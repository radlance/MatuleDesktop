package org.radlance.matuledesktop.presentation.cart

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
import androidx.compose.runtime.LaunchedEffect
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
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.email_full
import matuledesktop.composeapp.generated.resources.load_error
import matuledesktop.composeapp.generated.resources.name
import matuledesktop.composeapp.generated.resources.order_checkout
import matuledesktop.composeapp.generated.resources.order_data
import matuledesktop.composeapp.generated.resources.order_date
import matuledesktop.composeapp.generated.resources.place_order
import matuledesktop.composeapp.generated.resources.retry
import matuledesktop.composeapp.generated.resources.total_amount
import matuledesktop.composeapp.generated.resources.total_price
import matuledesktop.composeapp.generated.resources.user_data
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.radlance.matuledesktop.domain.auth.User
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

internal class CheckoutScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @Composable
    override fun Content() {
        val orderViewModel = koinViewModel<OrderViewModel>()

        val navigator = LocalNavigator.currentOrThrow
        val scrollState = rememberScrollState()
        var currentUser by remember { mutableStateOf(User()) }

        val loadContentResult by viewModel.catalogContent.collectAsState()
        val loadCartResult by viewModel.cartContent.collectAsState()
        val placeOrderResultUiState by viewModel.placeOrderResultUIState.collectAsState()
        val userUiState by orderViewModel.userUiState.collectAsState()

        val numberFormat = NumberFormat.getNumberInstance(Locale("ru"))
        var placeOrderButtonEnabled by remember { mutableStateOf(true) }

        placeOrderResultUiState.Show(
            onSuccess = { orderId ->
                LaunchedEffect(Unit) {
                    navigator.push(SuccessPlaceOrderScreen(imageLoader, viewModel, orderId))
                    viewModel.placeOrderUpdate()
                }
            },
            onError = {
                placeOrderButtonEnabled = true
            },
            onLoading = {
                placeOrderButtonEnabled = false
            },
            onUnauthorized = {}
        )

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
                    text = stringResource(Res.string.order_checkout),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 38.sp,
                    modifier = Modifier.padding(end = 15.dp)
                )

                Spacer(Modifier.height(10.dp))
            }

            loadContentResult.Show(
                onSuccess = { fetchContent ->
                    loadCartResult.Show(
                        onSuccess = { cartItems ->
                            val items = cartItems.filter { it.cartQuantity > 0 }

                            val totalPrice = items.sumOf { item ->
                                item.cartQuantity * fetchContent.products.first {
                                    it.id == item.productId
                                }.price
                            }

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
                                        val formatter = DateTimeFormatter.ofPattern(
                                            "dd.MM.yyyy",
                                            Locale.getDefault()
                                        )
                                        CheckoutDataItem(
                                            label = stringResource(Res.string.order_date),
                                            value = LocalDate.now().format(formatter)
                                        )

                                        CheckoutDataItem(
                                            label = stringResource(Res.string.total_price),
                                            value = "${numberFormat.format(totalPrice)} ₽"
                                        )

                                        CheckoutDataItem(
                                            label = stringResource(Res.string.total_amount),
                                            value = items.sumOf { it.cartQuantity }.toString()
                                        )

                                        items.forEach { cartItem ->
                                            val product = fetchContent.products.first {
                                                it.id == cartItem.productId
                                            }

                                            CheckoutDataItem(
                                                label = "${product.title}, ${cartItem.productSize} размер",
                                                value = "${numberFormat.format(product.price)} ₽ x${cartItem.cartQuantity}"
                                            )
                                        }

                                        Spacer(Modifier.height(40.dp))
                                    }

                                    VerticalScrollbar(
                                        modifier = Modifier.align(Alignment.CenterEnd)
                                            .fillMaxHeight(),
                                        adapter = rememberScrollbarAdapter(scrollState)
                                    )

                                    Button(
                                        onClick = viewModel::placeOrder,
                                        enabled = placeOrderButtonEnabled,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 15.dp)
                                            .align(Alignment.BottomCenter)
                                    ) {
                                        Text(text = stringResource(Res.string.place_order))
                                    }
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