package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.brand
import matuledesktop.composeapp.generated.resources.clasp_type
import matuledesktop.composeapp.generated.resources.color
import matuledesktop.composeapp.generated.resources.moisture_protection_type
import matuledesktop.composeapp.generated.resources.origin_country
import matuledesktop.composeapp.generated.resources.size
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

@Composable
internal fun SearchSettingsPlane(
    fetchContent: CatalogFetchContent,
    onCheckOriginCountry: (List<Int>) -> Unit,
    onCheckBrand: (List<Int>) -> Unit,
    onCheckSize: (List<Int>) -> Unit,
    onCheckClaspType: (List<Int>) -> Unit,
    onCheckMoistureProtectionType: (List<Int>) -> Unit,
    onCheckColor: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(64.dp),
            modifier = modifier.padding(top = 10.dp).horizontalScroll(scrollState)
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

            CheckboxGroup(
                groupLabel = stringResource(Res.string.color),
                items = fetchContent.colors,
                itemLabel = { it.name },
                itemId = { it.id },
                onSelectionChanged = onCheckColor
            )

            CheckboxGroup(
                groupLabel = stringResource(Res.string.size),
                items = fetchContent.sizes,
                itemLabel = { it.number.toString() },
                itemId = { it.number },
                onSelectionChanged = onCheckSize
            )

            CheckboxGroup(
                groupLabel = stringResource(Res.string.clasp_type),
                items = fetchContent.claspTypes,
                itemLabel = { it.name },
                itemId = { it.id },
                onSelectionChanged = onCheckClaspType
            )

            CheckboxGroup(
                groupLabel = stringResource(Res.string.moisture_protection_type),
                items = fetchContent.moistureProtectionTypes,
                itemLabel = { it.name },
                itemId = { it.id },
                onSelectionChanged = onCheckMoistureProtectionType
            )
        }

        HorizontalScrollbar(
            modifier = Modifier.fillMaxWidth(),
            adapter = rememberScrollbarAdapter(scrollState)
        )
    }
}