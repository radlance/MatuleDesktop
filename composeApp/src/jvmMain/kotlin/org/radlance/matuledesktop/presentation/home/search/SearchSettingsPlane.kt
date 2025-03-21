package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.origin_country
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

@Composable
fun SearchSettingsPlane(
    fetchContent: CatalogFetchContent,
    onCheckOriginCountry: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(top = 10.dp)) {
        Column {
            Text(text = stringResource(Res.string.origin_country))
            Spacer(Modifier.height(4.dp))
            CheckboxGroup(
                items = fetchContent.originCountries,
                itemLabel = { it.name },
                itemId = { it.id },
                onSelectionChanged = onCheckOriginCountry
            )
        }
    }
}