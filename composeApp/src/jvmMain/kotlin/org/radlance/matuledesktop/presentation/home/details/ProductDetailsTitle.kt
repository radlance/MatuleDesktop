package org.radlance.matuledesktop.presentation.home.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.popular_product
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.Product

@Composable
fun ProductDetailsTitle(
    fetchContent: CatalogFetchContent,
    selectedProduct: Product,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = fetchContent.brands.first {
                selectedProduct.brandId == it.id
            }.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = selectedProduct.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 25.sp,
        )

        if (selectedProduct.isPopular) {
            Spacer(Modifier.height(10.dp))

            Text(
                text = stringResource(Res.string.popular_product),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 25.sp,

                )

        }
    }
}