package xyz.wingio.hellish.domain.manager

import android.content.Context
import android.os.Build
import androidx.annotation.StringRes
import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager
import xyz.wingio.hellish.domain.manager.base.enumPreference
import xyz.wingio.hellish.ui.theme.CatppuccinLatte
import xyz.wingio.hellish.ui.theme.CatppuccinMocha

class PreferenceManager(
    context: Context
): BasePreferenceManager(context.getSharedPreferences("prefs", Context.MODE_PRIVATE)) {

    var baseTheme by enumPreference("base_theme", BaseTheme.SYSTEM)

    var theme by enumPreference("theme", if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) Theme.MONET else Theme.DEMON)

}

enum class BaseTheme {
    LIGHT,
    DARK,
    SYSTEM
}

enum class Theme(
    @StringRes val labelRes: Int,
    val light: ColorScheme?,
    val dark: ColorScheme?
) {
    MONET(
        labelRes = R.string.theme_monet,
        light = null,
        dark = null
    ),
    DEMON(
        R.string.theme_demon,
        light = dynamicColorScheme(Color.Red, isDark = false, style = PaletteStyle.Vibrant),
        dark = dynamicColorScheme(Color.Red, isDark = true, style = PaletteStyle.Vibrant)
    ),
    ACID(
        R.string.theme_acid,
        light = dynamicColorScheme(Color.Green, isDark = false, style = PaletteStyle.Vibrant),
        dark = dynamicColorScheme(Color.Green, isDark = true, style = PaletteStyle.Vibrant)
    ),
    TIDAL(
        R.string.theme_tidal,
        light = dynamicColorScheme(Color(0xFF0084FF), isDark = false, style = PaletteStyle.Vibrant),
        dark = dynamicColorScheme(Color(0xFF0084FF), isDark = true, style = PaletteStyle.Vibrant)
    ),
    TWILIGHT(
        R.string.theme_twilight,
        light = dynamicColorScheme(Color(0xFFFF00FF), isDark = false, style = PaletteStyle.Vibrant),
        dark = dynamicColorScheme(Color(0xFFFF00FF), isDark = true, style = PaletteStyle.Vibrant)
    ),
    CATPPUCCIN(
        R.string.theme_catppuccin,
        light = CatppuccinLatte,
        dark = CatppuccinMocha
    )
}