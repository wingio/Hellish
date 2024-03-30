package xyz.wingio.hellish.ui.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabNavigator
import xyz.wingio.hellish.util.MainNavTab

class MainScreen: Screen {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        TabNavigator(MainNavTab.DEMON_LIST.tab) {
            Scaffold(
                bottomBar = { TabNavBar() }
            ) {
                CurrentTab()
            }
        }
    }

    @Composable
    private fun TabNavBar() {
        val tabNavigator = LocalTabNavigator.current

        NavigationBar {
            MainNavTab.entries.forEach {
                NavigationBarItem(
                    selected = it.tab == tabNavigator.current,
                    onClick = { tabNavigator.current = it.tab },
                    label = {
                        Text(it.tab.options.title)
                    },
                    icon = {
                        Icon(
                            painter = it.tab.options.icon!!,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }

}