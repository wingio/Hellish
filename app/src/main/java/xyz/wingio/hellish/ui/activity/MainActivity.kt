package xyz.wingio.hellish.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import coil3.SingletonImageLoader
import org.koin.android.ext.android.get
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.domain.manager.PaletteManager
import xyz.wingio.hellish.ui.screen.auth.LandingScreen
import xyz.wingio.hellish.ui.screen.main.MainScreen
import xyz.wingio.hellish.ui.theme.HellishTheme
import xyz.wingio.hellish.util.coil.HighResInterceptor
import xyz.wingio.hellish.util.coil.PaletteGeneratorInterceptor

class MainActivity : ComponentActivity() {

    private val authManager: AuthManager = get()
    private val paletteManager: PaletteManager = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startScreen = if (authManager.onboarded) MainScreen() else LandingScreen()

        SingletonImageLoader.setSafe { ctx ->
            ImageLoader.Builder(ctx)
                .components {
                    add(HighResInterceptor()) // Should always be added first
                    add(PaletteGeneratorInterceptor(paletteManager))
                }
                .build()
        }

        setContent {
            HellishTheme {
                Navigator(startScreen) {
                    SlideTransition(navigator = it)
                }
            }
        }
    }

}