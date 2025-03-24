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
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.add_to_cart
import matuledesktop.composeapp.generated.resources.add_to_favorite
import org.jetbrains.compose.resources.stringResource
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
        val containerSize = LocalWindowInfo.current.containerSize

        val numberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))
        val scrollState = rememberScrollState()
        var selectedProductSize by remember { mutableStateOf(-1) }

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
                                    onClick = {},
                                    modifier = Modifier.size(52.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.FavoriteBorder,
                                        contentDescription = stringResource(Res.string.add_to_favorite)
                                    )
                                }

                                Spacer(Modifier.width(20.dp))

                                OutlinedButton(
                                    onClick = {},
                                    enabled = selectedProductSize != -1,
                                    modifier = Modifier.weight(1f).height(52.dp)
                                ) {
                                    Text(text = stringResource(Res.string.add_to_cart))
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
    }
}