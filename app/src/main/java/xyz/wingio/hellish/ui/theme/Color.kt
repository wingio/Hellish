package xyz.wingio.hellish.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AwardGold = Color(0xFFFFD700)
val AwardSilver = Color(0xFFC0C0C0)
val AwardBronze = Color(0xFFFF5733)
val AwardPlaced = Color(0xFFCCCCCC)

@Composable
fun getColorForPlacement(placement: Int): Color {
    return when (placement) {
        1 -> AwardGold
        2 -> AwardSilver
        3 -> AwardBronze
        in 3..50 -> MaterialTheme.colorScheme.tertiary
        in 51..100 -> MaterialTheme.colorScheme.onSurface
        else -> AwardPlaced
    }
}