package xyz.wingio.hellish.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LabeledDivider(
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    labelContainerColor: Color = MaterialTheme.colorScheme.surfaceColorAtElevation(LocalAbsoluteTonalElevation.current),
    borderColor: Color = DividerDefaults.color,
    thickness: Dp = DividerDefaults.Thickness
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        HorizontalDivider(
            color = borderColor,
            thickness = thickness,
            modifier = Modifier.fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .background(labelContainerColor)
                .padding(horizontal = 10.dp)
        ) {
            ProvideTextStyle(MaterialTheme.typography.labelLarge) {
                label()
            }
        }
    }
}