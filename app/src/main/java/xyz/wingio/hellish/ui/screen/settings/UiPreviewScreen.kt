package xyz.wingio.hellish.ui.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.component.BackButton
import xyz.wingio.hellish.ui.screen.settings.component.SettingsHeader

class UiPreviewScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        Scaffold(
            topBar = { TitleBar(scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { pv ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(pv)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                SettingsHeader(text = "Buttons")

                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    PreviewItem {
                        Button(onClick = { }) {
                            Text(text = "Button")
                        }

                        Button(onClick = { }, enabled = false) {
                            Text(text = "Button")
                        }
                    }

                    PreviewItem {
                        FilledTonalButton(onClick = { /*TODO*/ }) {
                            Text(text = "Tonal Button")
                        }

                        FilledTonalButton(onClick = { /*TODO*/ }, enabled = false) {
                            Text(text = "Tonal Button")
                        }
                    }

                    PreviewItem {
                        OutlinedButton(onClick = { /*TODO*/ }) {
                            Text(text = "Outlined Button")
                        }

                        OutlinedButton(onClick = { /*TODO*/ }, enabled = false) {
                            Text(text = "Outlined Button")
                        }
                    }

                    PreviewItem {
                        TextButton(onClick = { /*TODO*/ }) {
                            Text(text = "Text Button")
                        }

                        TextButton(onClick = { /*TODO*/ }, enabled = false) {
                            Text(text = "Text Button")
                        }
                    }
                }

                SettingsHeader(text = "Checkboxes")

                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    PreviewItem {
                        var switched by remember {
                            mutableStateOf(false)
                        }

                        Switch(checked = switched, onCheckedChange = { switched = it })

                        Switch(checked = switched, onCheckedChange = { switched = it }, enabled = false)
                    }

                    PreviewItem {
                        var switched by remember {
                            mutableStateOf(false)
                        }

                        RadioButton(selected = switched, onClick = { switched = !switched })

                        RadioButton(selected = switched, onClick = { switched = !switched }, enabled = false)
                    }

                    PreviewItem {
                        var switched by remember {
                            mutableStateOf(false)
                        }

                        Checkbox(checked = switched, onCheckedChange = { switched = it })

                        Checkbox(checked = switched, onCheckedChange = { switched = it }, enabled = false)
                    }
                }

                Spacer(modifier = Modifier.height(WindowInsets.systemBars.asPaddingValues(LocalDensity.current).calculateBottomPadding()))
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TitleBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        LargeTopAppBar(
            title = { Text(stringResource(R.string.settings_theme_preview)) },
            scrollBehavior = scrollBehavior,
            navigationIcon = { BackButton() }
        )
    }

    @Composable
    private fun PreviewItem(
        content: @Composable RowScope.() -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }

}