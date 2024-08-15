package xyz.wingio.hellish.ui.screen.settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Preview
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.BaseTheme
import xyz.wingio.hellish.domain.manager.PreferenceManager
import xyz.wingio.hellish.domain.manager.Theme
import xyz.wingio.hellish.ui.component.BackButton

class ThemeSettingsScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        val preferences: PreferenceManager = koinInject()
        var selectedCategory by remember {
            mutableStateOf(preferences.baseTheme)
        }

        Scaffold(
            topBar = { TitleBar(scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                modifier = Modifier.padding(pv)
            ) {
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    SegmentedButton(
                        selected = selectedCategory == BaseTheme.SYSTEM,
                        onClick = { selectedCategory = BaseTheme.SYSTEM },
                        shape = RoundedCornerShape(50, 0, 0, 50)
                    ) {
                        Text(stringResource(R.string.theme_system))
                    }

                    SegmentedButton(
                        selected = selectedCategory == BaseTheme.LIGHT,
                        onClick = { selectedCategory = BaseTheme.LIGHT },
                        icon = { Icon(imageVector = if (selectedCategory == BaseTheme.LIGHT) Icons.Filled.WbSunny else Icons.Outlined.WbSunny, contentDescription = null) },
                        shape = RectangleShape
                    ) {
                        Text(stringResource(R.string.theme_light))
                    }

                    SegmentedButton(
                        selected = selectedCategory == BaseTheme.DARK,
                        onClick = { selectedCategory = BaseTheme.DARK },
                        icon = { Icon(imageVector = if (selectedCategory == BaseTheme.DARK) Icons.Filled.NightsStay else Icons.Outlined.NightsStay, contentDescription = null) },
                        shape = RoundedCornerShape(0, 50, 50, 0)
                    ) {
                        Text(stringResource(R.string.theme_dark))
                    }
                }

                FlowRow(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    maxItemsInEachRow = 3,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    Theme.entries.forEach { theme ->
                        when {
                            theme == Theme.MONET && Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {}
                            else -> ThemeOption(
                                baseTheme = selectedCategory,
                                theme = theme,
                                selected = preferences.theme == theme && preferences.baseTheme == selectedCategory,
                                onSelected = {
                                    preferences.theme = theme
                                    preferences.baseTheme = selectedCategory
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TitleBar(
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        val navigator = LocalNavigator.currentOrThrow

        LargeTopAppBar(
            title = { Text(stringResource(R.string.settings_theme)) },
            scrollBehavior = scrollBehavior,
            navigationIcon = { BackButton() },
            actions = {
                IconButton(onClick = { navigator.push(UiPreviewScreen()) }) {
                    Icon(imageVector = Icons.Outlined.Preview, contentDescription = null)
                }
            }
        )
    }

    @Composable
    fun PreviewLine(
        contentColor: Color,
        containerColor: Color
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(contentColor, RoundedCornerShape(12))
                    .size(16.dp)
            )

            Box(
                modifier = Modifier
                    .background(containerColor, RoundedCornerShape(12))
                    .height(16.dp)
                    .fillMaxWidth()
            )
        }
    }

    @SuppressLint("NewApi")
    @Composable
    fun RowScope.ThemeOption(
        baseTheme: BaseTheme,
        theme: Theme,
        selected: Boolean,
        onSelected: () -> Unit
    ) {
        val isDark = when(baseTheme) {
            BaseTheme.SYSTEM -> isSystemInDarkTheme()
            BaseTheme.DARK -> true
            BaseTheme.LIGHT -> false
        }

        val colorScheme = when (theme) {
            Theme.MONET -> if (isDark) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
            else -> if (isDark) theme.dark!! else theme.light!!
        }

        val shape = RoundedCornerShape(12.dp)
        val previewShape = RoundedCornerShape(12)

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .clip(shape)
                .selectable(selected) { onSelected() }
                .background(
                    if (selected) colorScheme.primary else colorScheme.primaryContainer
                )
                .border(1.dp, colorScheme.primary, shape)
                .padding(16.dp)
                .width(80.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                modifier = Modifier
                    .shadow(3.dp, previewShape)
                    .background(colorScheme.surface, previewShape)
                    .padding(10.dp)
            ) {
                PreviewLine(contentColor = colorScheme.primary, containerColor = colorScheme.primaryContainer)
                PreviewLine(contentColor = colorScheme.secondary, containerColor = colorScheme.secondaryContainer)
                PreviewLine(contentColor = colorScheme.tertiary, containerColor = colorScheme.tertiaryContainer)
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.animateContentSize()
            ) {
                if (selected) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Text(
                    text = stringResource(theme.labelRes),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    color = if (selected) colorScheme.onPrimary else colorScheme.primary,
                    modifier = Modifier.basicMarquee()
                )
            }
        }
    }

}