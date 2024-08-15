package xyz.wingio.hellish.ui.theme

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import org.koin.compose.koinInject
import xyz.wingio.hellish.domain.manager.BaseTheme
import xyz.wingio.hellish.domain.manager.PreferenceManager
import xyz.wingio.hellish.domain.manager.Theme

@Composable
fun HellishTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current as ComponentActivity
    val preferences: PreferenceManager = koinInject()

    val isDark = when(preferences.baseTheme) {
        BaseTheme.SYSTEM -> isSystemInDarkTheme()
        BaseTheme.DARK -> true
        BaseTheme.LIGHT -> false
    }

    val colorScheme = when {
        preferences.theme == Theme.MONET && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isDark) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        preferences.theme == Theme.MONET -> Theme.DEMON.dark!! // Prevent crash if user manages to force dynamic theme on below A12

        isDark-> preferences.theme.dark!!
        !isDark -> preferences.theme.light!!
        else -> Theme.DEMON.dark!!
    }

    val systemBarStyle = remember(isDark) {
        SystemBarStyle.auto(
            lightScrim = colorScheme.scrim.toArgb(),
            darkScrim = colorScheme.scrim.toArgb(),
            detectDarkMode = { _ -> isDark }
        )
    }

    context.enableEdgeToEdge(systemBarStyle)

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}