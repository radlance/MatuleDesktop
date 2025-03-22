package org.radlance.matuledesktop.presentation.home.search

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import matuledesktop.composeapp.generated.resources.Res
import matuledesktop.composeapp.generated.resources.ic_search
import matuledesktop.composeapp.generated.resources.ic_search_settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource
import org.radlance.matuledesktop.domain.product.CatalogFetchContent

@Composable
fun SearchField(
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    onSearchClick: (String) -> Unit,
    fetchContent: CatalogFetchContent,
    onCheckOriginCountry: (List<Int>) -> Unit,
    onCheckBrand: (List<Int>) -> Unit,
    onCheckSize: (List<Int>) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchState by remember { mutableStateOf<SearchState>(SearchState.Collapsed) }

    Column(modifier = Modifier.animateContentSize()) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .height(52.dp)
                    .animateContentSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Spacer(Modifier.width(31.dp))

                    Image(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "ic_search",
                        modifier = Modifier
                            .padding(5.dp)
                            .clickable { onSearchClick(value) }
                    )

                    Spacer(Modifier.width(12.dp))


                    Box(contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(
                                text = hint,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 20.sp
                            )
                        }

                        BasicTextField(
                            value = value,
                            onValueChange = onValueChange,
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 20.sp
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = { onSearchClick(value) }
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            IconButton(
                onClick = { searchState = searchState.inverse() }, modifier = Modifier
                    .clip(CircleShape)
                    .size(52.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.ic_search_settings),
                    contentDescription = "SearchSettingsIcon",
                    tint = Color.White
                )
            }
        }

        searchState.Show(
            fetchContent = fetchContent,
            onCheckOriginCountry = onCheckOriginCountry,
            onCheckBrand = onCheckBrand,
            onCheckSize = onCheckSize
        )
    }
}