package org.radlance.matuledesktop.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import org.radlance.matuledesktop.domain.product.Product
import org.radlance.matuledesktop.presentation.common.ProductCardImage
import java.text.NumberFormat

@Composable
fun CartItemCard(
    imageLoader: ImageLoader,
    product: Product,
    size: Int,
    quantity: Int,
    numberFormat: NumberFormat,
    onChangeQuantityClick: (quantity: Int, increment: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp))) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ProductCardImage(
                imageLoader = imageLoader,
                imageUrl = product.imageUrl,
                modifier = Modifier.width(120.dp)
            )

            Spacer(Modifier.width(10.dp))

            Column(modifier = Modifier.weight(6f)) {
                Text(
                    text = product.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Цвет: ${product.colors.joinToString(separator = " / ") { it.name }}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Размер: $size",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )
            }

            Spacer(Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {

                OutlinedIconButton(
                    onClick = {
                        onChangeQuantityClick(
                            quantity.dec(),
                            false
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(Modifier.width(4.dp))

                Text(
                    text = quantity.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 20.sp
                )

                Spacer(Modifier.width(4.dp))

                OutlinedIconButton(
                    onClick = {
                        onChangeQuantityClick(
                            quantity.inc(),
                            true
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = "${numberFormat.format(product.price)} ₽",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp
            )

            Spacer(Modifier.width(20.dp))
        }
    }
}