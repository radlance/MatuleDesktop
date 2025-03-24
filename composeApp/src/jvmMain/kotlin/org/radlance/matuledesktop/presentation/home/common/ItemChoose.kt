package org.radlance.matuledesktop.presentation.home.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun ItemChoose(
    categoryTitle: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val boxBackgroundColor by animateColorAsState(
        if (selected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
    )

    val textColor by animateColorAsState(
        if (selected) {
            Color.White
        } else {
            TextFieldDefaults.colors().focusedTextColor
        }
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(boxBackgroundColor)
            .alpha(if (enabled) 1f else 0.5f)
    ) {
        Text(
            text = categoryTitle,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = FontWeight.Normal,
            lineHeight = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 3.dp).alpha(if (enabled) 1f else 0.5f)
        )
    }
}