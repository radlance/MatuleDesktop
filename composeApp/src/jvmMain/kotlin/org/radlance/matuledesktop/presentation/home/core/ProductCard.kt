package org.radlance.matuledesktop.presentation.home.core

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Size
import org.radlance.matuledesktop.domain.product.Product
import java.text.NumberFormat
import java.util.Locale


@Composable
internal fun ProductCard(
    product: Product,
    onLikeClick: () -> Unit,
    onCartClick: () -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.clickable { onCardClick(product.id) }) {
        Column(modifier = Modifier.fillMaxWidth()) {

            ProductCardImage(
                imageUrl = product.imageUrl,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .animateContentSize()
                    .fillMaxWidth()
                    .heightIn(max = 450.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )

            ProductCardDetails(product, onCartClick, onLikeClick)
        }
    }
}

@Composable
private fun ProductCardImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(PlatformContext.INSTANCE).size(Size.ORIGINAL)
            .data(imageUrl)
            .build(),
        imageLoader = ImageLoader(PlatformContext.INSTANCE)
            .newBuilder()
            .crossfade(true)
            .build(),
        contentDescription = "shoe_example",
        contentScale = ContentScale.FillWidth,
        loading = {
            Box(modifier = Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        },

        modifier = modifier
    )
}

@Composable
private fun ProductCardDetails(product: Product, onCartClick: () -> Unit, onLikeClick: () -> Unit) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.of("ru"))

    Spacer(Modifier.height(12.dp))

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text(
                text = " ${numberFormat.format(product.price)} ₽",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 9.dp)
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = product.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 20.sp,
                modifier = Modifier.padding(start = 9.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = onCartClick) {
            val addIcon = if (product.quantityInCart == 0) {
                Icons.Default.Add
            } else {
                Icons.Default.ShoppingCart
            }

            Icon(imageVector = addIcon, contentDescription = null)
        }

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

        Spacer(modifier = Modifier.width(8.dp))
    }

    Spacer(Modifier.height(4.dp))
}