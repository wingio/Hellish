package xyz.wingio.hellish.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.core.model.ScreenModelStore
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.ui.component.BackButton
import xyz.wingio.hellish.ui.screen.auth.LandingScreen
import xyz.wingio.hellish.ui.screen.demonlist.DemonListTab
import xyz.wingio.hellish.ui.screen.settings.component.SettingsCategory
import xyz.wingio.hellish.ui.screen.settings.dialog.SignOutDialog
import xyz.wingio.hellish.ui.screen.settings.viewmodel.SettingsViewModel
import xyz.wingio.hellish.util.Constants

class SettingsScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: SettingsViewModel = getScreenModel()
        val authManager: AuthManager = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        if (viewModel.confirmSignOutDialogOpened) {
            SignOutDialog(
                onDismiss = { viewModel.closeDialog() },
                onConfirm = {
                    viewModel.closeDialog()
                    authManager.signOut()
                    navigator.replaceAll(LandingScreen())
                    ScreenModelStore.onDispose(DemonListTab())
                }
            )
        }

        Scaffold(
            topBar = { TitleBar(scrollBehavior = scrollBehavior, viewModel = viewModel) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier.padding(pv)
            ) {
                SettingsCategory(
                    icon = Icons.Outlined.Palette,
                    text = stringResource(R.string.settings_theme),
                    subtext = stringResource(R.string.settings_theme_description),
                    destination = ::ThemeSettingsScreen
                )

                SettingsCategory(
                    icon = Icons.Outlined.Info,
                    text = stringResource(R.string.about_title),
                    subtext = Constants.VERSION_STRING.replace("%%APPNAME%%", stringResource(R.string.app_name)),
                    destination = ::AboutScreen
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TitleBar(
        scrollBehavior: TopAppBarScrollBehavior,
        viewModel: SettingsViewModel
    ) {
        LargeTopAppBar(
            title = { Text(stringResource(R.string.settings_title)) },
            scrollBehavior = scrollBehavior,
            navigationIcon = { BackButton() },
            actions = {
                IconButton(
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        viewModel.openDialog()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.Logout,
                        contentDescription = stringResource(R.string.action_sign_out)
                    )
                }
            }
        )
    }

}