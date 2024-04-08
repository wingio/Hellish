package xyz.wingio.hellish.ui.screen.demonlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.wingio.hellish.ui.theme.getColorForPlacement
import xyz.wingio.hellish.util.thenIf

@Composable
fun DemonPlacementBadge(
    place: Int
) {
    val placementColor = getColorForPlacement(place)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = placementColor.copy(alpha = 0.2f),
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = placementColor,
                shape = CircleShape
            )
            .thenIf(place in 1..3) {
                size(34.dp)
            }
            .thenIf(place > 3) {
                padding(9.dp, 3.dp)
            }
    ) {
        Text(
            text = "#$place",
            style = MaterialTheme.typography.labelLarge,
            color = placementColor
        )
    }
}