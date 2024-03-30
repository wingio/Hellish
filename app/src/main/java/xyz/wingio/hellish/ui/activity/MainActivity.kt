package xyz.wingio.hellish.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import xyz.wingio.hellish.ui.screen.main.MainScreen
import xyz.wingio.hellish.ui.theme.HellishTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HellishTheme {
                Navigator(MainScreen())
            }
        }
    }

}