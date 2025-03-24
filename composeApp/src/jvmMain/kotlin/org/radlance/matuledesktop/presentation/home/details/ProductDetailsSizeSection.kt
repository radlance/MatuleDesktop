package org.radlance.matuledesktop.presentation.home.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.choose_size
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.Product

@Composable
fun ProductDetailsSizeSection(
    selectedProduct: Product,
    selectedProductSize: Int,
    onSizeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "${stringResource(Res.string.choose_size)}:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 25.sp
        )

        Spacer(Modifier.height(12.dp))

        SizesRow(
            sizes = selectedProduct.sizes,
            onSizeClick = onSizeClick,
            selectedSize = selectedProductSize
        )
    }
}