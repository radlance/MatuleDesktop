package org.radlance.matuledesktop.presentation.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.clasp_type
import matuledesktop.composeapp.generated.resources.color
import matuledesktop.composeapp.generated.resources.moisture_protection_type
import matuledesktop.composeapp.generated.resources.origin_country
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.Product

@Composable
internal fun ProductDetailsProperties(
    fetchContent: CatalogFetchContent,
    selectedProduct: Product,
    modifier: Modifier = Modifier
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp), modifier = modifier) {
        ProductPropertySection(
            property = stringResource(Res.string.origin_country),
            value = fetchContent.originCountries.first {
                selectedProduct.originCountryId == it.id
            }.name
        )

        ProductPropertySection(
            property = stringResource(Res.string.clasp_type),
            value = fetchContent.claspTypes.first {
                selectedProduct.claspTypeId == it.id
            }.name
        )

        ProductPropertySection(
            property = stringResource(Res.string.moisture_protection_type),
            value = fetchContent.moistureProtectionTypes.first {
                selectedProduct.moistureProtectionTypeId == it.id
            }.name
        )
    }

    Spacer(Modifier.height(20.dp))

    Row {
        Text(text = "${stringResource(Res.string.color)}:")
        Spacer(Modifier.width(12.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            selectedProduct.colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color.Black,
                            shape = CircleShape,
                        )
                        .background(
                            Color(
                                red = color.red,
                                green = color.green,
                                blue = color.blue
                            )
                        )
                )
            }
        }
    }
}