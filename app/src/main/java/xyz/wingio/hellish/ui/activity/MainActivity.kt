package xyz.wingio.hellish.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.koin.android.ext.android.get
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.ui.screen.auth.LoginScreen
import xyz.wingio.hellish.ui.screen.main.MainScreen
import xyz.wingio.hellish.ui.theme.HellishTheme

class MainActivity : ComponentActivity() {

    private val authManager: AuthManager = get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startScreen = if (authManager.isLoggedIn) MainScreen() else LoginScreen()

        setContent {
            HellishTheme {
                Navigator(startScreen) {
                    SlideTransition(navigator = it)
                }
            }
        }
    }

}