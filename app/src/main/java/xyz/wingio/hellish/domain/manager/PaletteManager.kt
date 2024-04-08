package xyz.wingio.hellish.domain.manager

import android.content.Context
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager
import xyz.wingio.hellish.util.getHighResUrl

/**
 * Manages generated themes for images based on their dominant color
 *
 * @see [xyz.wingio.hellish.util.coil.PaletteGeneratorInterceptor]
 */
class PaletteManager(
    context: Context,
): BasePreferenceManager(
    context.getSharedPreferences("palette", Context.MODE_PRIVATE),
    commit = true
) {

    /**
     * Seed colors pulled from images, keys are image urls
     */
    private val seedColors = mutableStateMapOf<String, Color>()

    init {
        // Pull all cached seed colors
        val persistedColors = prefs.all.map { (url, colorInt) ->
            url to Color(colorInt.toString().toInt())
        }.toMap()
        seedColors.putAll(persistedColors)
    }

    fun hasSeedColor(url: String) = seedColors.containsKey(url) || seedColors.containsKey(getHighResUrl(url))

    fun setSeedColor(url: String, color: Color) {
        putInt(url, color.toArgb())
        seedColors[url] = color
    }

    /**
     * Generates a theme based on Material 3 from an images dominant color.
     *
     * @param url The images url
     * @param paletteStyle Style of the color scheme
     */
    fun getThemeForImage(
        url: String,
        isDark: Boolean = true,
        paletteStyle: PaletteStyle = PaletteStyle.TonalSpot
    ): ColorScheme {
        val imageSeedColor = seedColors[getHighResUrl(url)] ?: seedColors[url] ?: Color.Red
        return dynamicColorScheme(imageSeedColor, isDark = isDark, style = paletteStyle)
    }

}