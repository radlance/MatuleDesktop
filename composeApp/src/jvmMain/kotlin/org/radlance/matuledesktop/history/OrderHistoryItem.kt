package org.radlance.matuledesktop.history

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import org.radlance.matuledesktop.domain.history.Order
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.presentation.common.ProductCardImage

@Composable
fun OrderHistoryItem(
    order: Order,
    products: List<Product>,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()


    Column(verticalArrangement = Arrangement.Center, modifier = modifier) {

        Box(modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
            Column {
                Text(
                    text = "Есть в заказе",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )
                Spacer(Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.horizontalScroll(scrollState).padding(bottom = 4.dp)
                ) {
                    val imageSet =
                        order.orderItems.map { order ->
                            products.first { it.id == order.productId }.imageUrl
                        }.toSet()

                    imageSet.forEach { image ->
                        ProductCardImage(
                            imageLoader = imageLoader,
                            imageUrl = image,
                            modifier = Modifier.width(100.dp).clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }
            HorizontalScrollbar(
                modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }
    }
}