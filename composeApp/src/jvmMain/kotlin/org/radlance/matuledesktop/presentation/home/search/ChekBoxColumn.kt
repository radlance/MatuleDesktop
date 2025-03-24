package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.all
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.ProductColor

@Composable
fun <T> CheckboxGroup(
    groupLabel: String,
    items: List<T>,
    itemLabel: (T) -> String,
    itemId: (T) -> Int,
    onSelectionChanged: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val childCheckedStates = remember {
        mutableStateListOf(*BooleanArray(items.size) { true }.toTypedArray())
    }

    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column(modifier = modifier) {
        Text(text = groupLabel)
        Spacer(Modifier.height(4.dp))

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(180.dp)) {
            Text(text = stringResource(Res.string.all), modifier = Modifier.weight(1f))
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    val newState = parentState != ToggleableState.On
                    childCheckedStates.fill(newState)
                    onSelectionChanged(if (newState) items.map(itemId) else emptyList())
                }
            )
        }

        Box(modifier = Modifier.height(150.dp)) {
            Column(
                modifier = Modifier.fillMaxHeight().verticalScroll(scrollState),
                verticalArrangement = Arrangement.Center
            ) {
                items.forEachIndexed { index, item ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.width(180.dp)) {

                        (item as? ProductColor)?.let { color ->
                            Column {
                                Spacer(Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .size(18.dp)
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
                            Spacer(Modifier.width(8.dp))
                        }

                        Text(text = itemLabel(item), modifier = Modifier.weight(1f))
                        Checkbox(
                            checked = childCheckedStates[index],
                            onCheckedChange = { isChecked ->
                                childCheckedStates[index] = isChecked
                                onSelectionChanged(
                                    items.filterIndexed { i, _ -> childCheckedStates[i] }.map(itemId)
                                )
                            }
                        )
                    }
                }
            }
            VerticalScrollbar(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                adapter = rememberScrollbarAdapter(scrollState)
            )
        }
    }
}
