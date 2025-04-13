package org.radlance.matuledesktop.presentation.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.return_to_shopping
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.navigation.NavigationTab
import org.radlance.matuledesktop.presentation.common.ProductViewModel

internal class SuccessPlaceOrderScreen(
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel,
    private val orderId: Int
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val tabNavigator = LocalTabNavigator.current

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "#$orderId",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 38.sp,
                modifier = Modifier.padding(end = 15.dp)
            )

            Column(
                modifier = Modifier.weight(1f).padding(end = 15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "CheckCircle",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(200.dp)
                )

                Spacer(Modifier.height(10.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle()) {
                            append("Заказ ")
                        }

                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("#${orderId}")
                        }

                        withStyle(SpanStyle()) {
                            append(" успешно оформлен")
                        }
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 19.sp
                )

                Spacer(Modifier.height(10.dp))


                Button(
                    onClick = {
                        tabNavigator.current = NavigationTab.Home(
                            imageLoader = imageLoader,
                            viewModel = viewModel,
                            resetNavigation = true
                        )
                        repeat(2) { navigator.pop() }
                    }
                ) {
                    Text(text = stringResource(Res.string.return_to_shopping))
                }
            }
        }
    }
}