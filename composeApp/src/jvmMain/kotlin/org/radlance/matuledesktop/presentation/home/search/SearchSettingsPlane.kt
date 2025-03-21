package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.all
import matuledesktop.composeapp.generated.resources.origin_country
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent
import org.radlance.matuledesktop.domain.product.OriginCountry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchSettingsPlane(
    fetchContent: CatalogFetchContent,
    modifier: Modifier = Modifier
) {
    val allCountriesItem = OriginCountry(id = 0, name = stringResource(Res.string.all))
    val allDropDownMenuItems = listOf(allCountriesItem) + fetchContent.originCountries

    var expanded by remember { mutableStateOf(false) }
    var dropDownMenuValue by remember { mutableStateOf(allDropDownMenuItems.first().name) }

    Column(modifier) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = stringResource(Res.string.origin_country))
        Spacer(modifier = Modifier.height(2.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it },
        ) {
            OutlinedTextField(
                value = dropDownMenuValue,
                onValueChange = {},
                label = { },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                allDropDownMenuItems.forEach { country ->
                    DropdownMenuItem(
                        text = { Text(country.name) },
                        onClick = {
                            dropDownMenuValue = country.name
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}