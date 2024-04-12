package xyz.wingio.hellish.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

private val Rosewater = Color(0xFFF5E0DC)
private val Flamingo = Color(0xFFF2CDCD)
private val Pink = Color(0xFFf5c2e7)
private val Muave = Color(0xFFcba6f7)
private val Red = Color(0xFFf38ba8)
private val Maroon = Color(0xFFeba0ac)
private val Peach = Color(0xFFFAB387)
private val Yellow = Color(0xFFF9E2AF)
private val Green = Color(0xFFA6E3A1)
private val Teal = Color(0xFF94E2D5)
private val Sky = Color(0xFF89DCEB)
private val Sapphire = Color(0xFF74C7EC)
private val Blue = Color(0xFF89B4FA)
private val Lavender = Color(0xFFB4BEFE)
private val Text = Color(0xFFCDD6F4)
private val Subtext1 = Color(0xFFBAC2DE)
private val Subtext0 = Color(0xFFA6ADC8)
private val Overlay2 = Color(0xFF9399B2)
private val Overlay1 = Color(0xFF7F849C)
private val Overlay0 = Color(0xFF6C7086)
private val Surface2 = Color(0xFF585B70)
private val Surface1 = Color(0xFF45475A)
private val Surface0 = Color(0xFF313244)
private val Base = Color(0xFF1E1E2E)
private val Mantle = Color(0xFF181825)
private val Crust = Color(0xFF11111B)

val CatppuccinMocha = ColorScheme(
    primary = Muave,
    onPrimary = Surface0,
    primaryContainer = Surface0,
    onPrimaryContainer = Surface0,
    inversePrimary = Blue,
    secondary = Sapphire,
    onSecondary = Surface1,
    secondaryContainer = Surface1,
    onSecondaryContainer = Text,
    tertiary = Teal,
    onTertiary = Surface2,
    tertiaryContainer = Surface0,
    onTertiaryContainer = Surface2,
    background = Crust,
    onBackground = Text,
    surface = Crust,
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
    surfaceContainerHigh = Surface1,
    surfaceContainerHighest = Surface0,
    surfaceContainerLow = Mantle,
    surfaceContainerLowest = Crust,
)