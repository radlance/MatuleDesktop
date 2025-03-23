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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.presentation.common.ProductCardImage
import org.radlance.matuledesktop.presentation.common.ProductViewModel
import java.text.NumberFormat
import java.util.Locale

internal class ProductDetailsScreen(
    private val selectedProduct: Product,
    private val fetchContent: CatalogFetchContent,
    private val imageLoader: ImageLoader,
    private val viewModel: ProductViewModel
) : Screen {

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val numberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier.fillMaxSize().padding(top = 15.dp, end = 15.dp),
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
                        Box(modifier = Modifier.weight(1.5f)) {
                            ProductCardImage(
                                imageLoader,
                                imageUrl = selectedProduct.imageUrl
                            )
                        }

                        Spacer(Modifier.weight(0.5f))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = fetchContent.brands.first {
                                    selectedProduct.brandId == it.id
                                }.name,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )

                            Spacer(Modifier.height(10.dp))

                            Text(
                                text = selectedProduct.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 25.sp,
                            )

                            Spacer(Modifier.height(20.dp))

                            Text(
                                text = " ${numberFormat.format(selectedProduct.price)} ₽",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 25.sp,
                            )
                        }
                    }
                }

                VerticalScrollbar(
                    modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                    adapter = rememberScrollbarAdapter(scrollState)
                )
            }
        }
    }
}