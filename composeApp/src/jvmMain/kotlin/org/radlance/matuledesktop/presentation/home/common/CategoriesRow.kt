package org.radlance.matuledesktop.presentation.home.common

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.all
import matuledesktop.composeapp.generated.resources.categories
import org.jetbrains.compose.resources.stringResource
import org.radlance.matuledesktop.domain.product.Category

@Composable
internal fun CategoriesRow(
    categories: List<Category>,
    onCategoryClick: (Int?) -> Unit,
    selectedCategoryId: Int,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(end = 15.dp)
) {
    val allCategory = Category(id = 0, title = stringResource(Res.string.all))
    val allCategories = listOf(allCategory) + categories
    val lazyListState = rememberLazyListState()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(Res.string.categories),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 19.sp,
            modifier = Modifier
                .align(Alignment.Start)
        )

        Spacer(Modifier.height(16.dp))

        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(13.dp),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(items = allCategories, key = { category -> category.id }) { categoryItem ->
                ItemChoose(
                    categoryTitle = categoryItem.title,
                    selected = categoryItem.id == selectedCategoryId,
                    modifier = Modifier
                        .width(108.dp)
                        .clickable {
                            val selectedCategory = if (categoryItem != allCategory) {
                                categoryItem.id
                            } else {
                                null
                            }
                            onCategoryClick(selectedCategory)
                        }
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