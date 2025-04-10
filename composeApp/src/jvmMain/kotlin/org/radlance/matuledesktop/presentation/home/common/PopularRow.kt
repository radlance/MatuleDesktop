package org.radlance.matuledesktop.presentation.home.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.all
import matuledesktop.composeapp.generated.resources.popular
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.presentation.common.ProductCard

@Composable
internal fun PopularRow(
    imageLoader: ImageLoader,
    products: List<Product>,
    onLikeClick: (productId: Int) -> Unit,
    onCardClick: (Product) -> Unit,
    navigateToPopular: () -> Unit,
    modifier: Modifier = Modifier
) {
    val popularProducts = products.filter { it.isPopular }.take(3)

    if (popularProducts.isNotEmpty()) {
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(Res.string.popular),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 24.sp
                )

                Text(
                    text = stringResource(Res.string.all),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 16.sp,
                    modifier = Modifier.clickable { navigateToPopular() }
                )
            }

            Spacer(Modifier.height(16.dp))



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                popularProducts.forEach { product ->
                    ProductCard(
                        imageLoader = imageLoader,
                        onLikeClick = {
                            onLikeClick(product.id)
                        },
                        product = product,
                        onCardClick = onCardClick,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}