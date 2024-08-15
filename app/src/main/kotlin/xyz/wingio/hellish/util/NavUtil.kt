package xyz.wingio.hellish.util

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.hellish.ui.screen.demonlist.DemonListTab
import xyz.wingio.hellish.ui.screen.profile.ProfileTab

enum class MainNavTab(val tab: Tab) {
    DEMON_LIST(DemonListTab()),
    PROFILE(ProfileTab())
}

fun Navigator.navigate(screen: Screen) {
    if (parent == null) return push(screen)
    else parent!!.navigate(screen)
}

@Composable
@SuppressLint("ComposableNaming")
fun Tab.TabOptions(
    @StringRes name: Int,
    icon: ImageVector
): TabOptions {
    return TabOptions(
        0u,
        title = stringResource(name),
        icon = rememberVectorPainter(icon)
    )
}