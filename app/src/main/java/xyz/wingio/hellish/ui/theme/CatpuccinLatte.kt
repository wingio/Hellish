package xyz.wingio.hellish.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

private val Rosewater = Color(0xFFDC8A78)
private val Flamingo = Color(0xFFDD7878)
private val Pink = Color(0xFFEA76CB)
private val Muave = Color(0xFF8839EF)
private val Red = Color(0xFFD20F39)
private val Maroon = Color(0xFFE64553)
private val Peach = Color(0xFFFE640B)
private val Yellow = Color(0xFFDF8E1D)
private val Green = Color(0xFF40A02B)
private val Teal = Color(0xFF179299)
private val Sky = Color(0xFF04A5E5)
private val Sapphire = Color(0xFF209FB5)
private val Blue = Color(0xFF1E66F5)
private val Lavender = Color(0xFF7287FD)
private val Text = Color(0xFF4C4F69)
private val Subtext1 = Color(0xFF5C5F77)
private val Subtext0 = Color(0xFF6C6F85)
private val Overlay2 = Color(0xFF7C7F93)
private val Overlay1 = Color(0xFF8C8FA1)
private val Overlay0 = Color(0xFF9CA0B0)
private val Surface2 = Color(0xFFACB0BE)
private val Surface1 = Color(0xFFBCC0CC)
private val Surface0 = Color(0xFFCCD0DA)
private val Base = Color(0xFFEFF1F5)
private val Mantle = Color(0xFFE6E9EF)
private val Crust = Color(0xFFDCE0E8)

val CatppuccinLatte = ColorScheme(
    primary = Muave,
    onPrimary = Base,
    primaryContainer = Crust,
    onPrimaryContainer = Text,
    inversePrimary = Blue,
    secondary = Sapphire,
    onSecondary = Surface1,
    secondaryContainer = Surface0,
    onSecondaryContainer = Text,
    tertiary = Sky,
    onTertiary = Surface2,
    tertiaryContainer = Surface1,
    onTertiaryContainer = Surface0,
    background = Base,
    onBackground = Text,
    surface = Base,
    onSurface = Text,
    surfaceVariant = Surface2,
    onSurfaceVariant = Text,
    surfaceTint = Overlay0,
    inverseSurface = Text,
    inverseOnSurface = Surface0,
    error = Red,
    onError = Base,
    errorContainer = Maroon,
    onErrorContainer = Base,
    outline = Text,
    outlineVariant = Rosewater,
    scrim = Crust,
    surfaceBright = Surface1,
    surfaceDim = Mantle,
    surfaceContainer = Mantle,
    surfaceContainerHigh = Surface0,
    surfaceContainerHighest = Surface1,
    surfaceContainerLow = Mantle,
    surfaceContainerLowest = Crust,
)