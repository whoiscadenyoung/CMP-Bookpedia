package com.jj.bookpedia.book.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jj.bookpedia.core.presentation.LightBlue
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ChipSize {
    SMALL, REGULAR
}

@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.REGULAR,
    chipContent: @Composable RowScope.() -> Unit
) {
    Box(
        modifier = modifier
            .widthIn(
                min = when (size) {
                    ChipSize.SMALL -> 50.dp
                    ChipSize.REGULAR -> 80.dp
                }
            )
            .clip(RoundedCornerShape((16.dp)))
            .background(LightBlue)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            chipContent()
        }
    }

}

@Preview
@Composable
fun BookChipPreview() {
    MaterialTheme {
        BookChip {

        }
    }
}