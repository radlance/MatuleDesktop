package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.brand
import matuledesktop.composeapp.generated.resources.origin_country
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

@Composable
fun SearchSettingsPlane(
    fetchContent: CatalogFetchContent,
    onCheckOriginCountry: (List<Int>) -> Unit,
    onCheckBrand: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(64.dp),
        modifier = modifier.padding(top = 10.dp)
    ) {
        CheckboxGroup(
            groupLabel = stringResource(Res.string.origin_country),
            items = fetchContent.originCountries,
            itemLabel = { it.name },
            itemId = { it.id },
            onSelectionChanged = onCheckOriginCountry
        )

        CheckboxGroup(
            groupLabel = stringResource(Res.string.brand),
            items = fetchContent.brands,
            itemLabel = { it.name },
            itemId = { it.id },
            onSelectionChanged = onCheckBrand
        )
    }
}