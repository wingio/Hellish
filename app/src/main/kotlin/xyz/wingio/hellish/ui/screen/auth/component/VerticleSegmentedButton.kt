package xyz.wingio.hellish.ui.screen.auth.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerticalSegmentedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enabledTextColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.38f),
    enabledContainerColor: Color = MaterialTheme.colorScheme.primary,
    disabledContainerColor: Color = MaterialTheme.colorScheme.onSurface.copy(0.12f)
) {
    val textColor = if (enabled) enabledTextColor else disabledTextColor
    val containerColor = if (enabled) enabledContainerColor else disabledContainerColor

    Text(
        text = text,
        color = textColor,
        style = MaterialTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
        fontSize = 15.sp,
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(containerColor)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(16.dp)
    )
}