package org.radlance.matuledesktop.presentation.home.details

import androidx.compose.foundation.HorizontalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.radlance.matuledesktop.domain.product.ProductSize
import org.radlance.matuledesktop.presentation.home.common.ItemChoose

@Composable
internal fun SizesRow(
    sizes: List<ProductSize>,
    onSizeClick: (Int) -> Unit,
    selectedSizeId: Int,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(end = 15.dp)
) {
    val lazyListState = rememberLazyListState()

    Column(modifier = modifier) {
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            contentPadding = paddingValues
        ) {
            items(items = sizes, key = { size -> size.size }) { size ->
                ItemChoose(
                    categoryTitle = size.size.toString(),
                    selected = size.sizeId == selectedSizeId,
                    enabled = size.quantity != 0,
                    modifier = Modifier
                        .width(50.dp)
                        .then(
                            if (size.quantity != 0) {
                                Modifier.clickable { onSizeClick(size.sizeId) }
                            } else {
                                Modifier
                            }
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        HorizontalScrollbar(
            modifier = Modifier.fillMaxWidth(),
            adapter = rememberScrollbarAdapter(lazyListState)
        )
    }
}