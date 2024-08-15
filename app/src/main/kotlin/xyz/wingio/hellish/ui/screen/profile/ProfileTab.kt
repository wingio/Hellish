package xyz.wingio.hellish.ui.screen.profile

import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.icon.filled.GDPlayer
import xyz.wingio.hellish.ui.icon.outlined.GDPlayer
import xyz.wingio.hellish.util.TabOptions

class ProfileTab: Tab {

    override val options: TabOptions
        @Composable get() = TabOptions(
            name = R.string.tab_profile,
            icon = Icons.Filled.GDPlayer
        )

    @Composable
    override fun Content() {

    }

}