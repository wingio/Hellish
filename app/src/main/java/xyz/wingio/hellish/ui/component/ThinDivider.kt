package xyz.wingio.hellish.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThinDivider(
    modifier: Modifier = Modifier
) {
    HorizontalDivider(
        color = LocalContentColor.current.copy(alpha = 0.3f),
        thickness = 0.5.dp,
        modifier = modifier
            .fillMaxWidth()
    )
}