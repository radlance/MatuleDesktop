package org.radlance.matuledesktop.presentation.common

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import org.radlance.matuledesktop.domain.product.Product
import java.text.NumberFormat
import java.util.Locale


@Composable
internal fun ProductCard(
    imageLoader: ImageLoader,
    product: Product,
    onLikeClick: () -> Unit,
    onCardClick: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.clickable { onCardClick(product) }) {
        Column(modifier = Modifier.fillMaxWidth()) {
            ProductCardImage(
                imageLoader = imageLoader,
                imageUrl = product.imageUrl,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .animateContentSize()
                    .fillMaxWidth()
                    .heightIn(max = 450.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            ProductCardDetails(product, onLikeClick)
            Spacer(Modifier.height(4.dp))
        }
    }
}

@Composable
private fun ProductCardDetails(product: Product, onLikeClick: () -> Unit) {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru"))

    Spacer(Modifier.height(12.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(4f)) {
            Text(
                text = "${numberFormat.format(product.price)} ₽",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 9.dp)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = product.title,
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 9.dp)
            )
        }

        Spacer(Modifier.width(20.dp))

        IconButton(onClick = onLikeClick) {
            if (product.isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Red
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder, contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}