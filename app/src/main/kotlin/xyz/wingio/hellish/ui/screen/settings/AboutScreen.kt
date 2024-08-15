package xyz.wingio.hellish.ui.screen.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material.icons.outlined.BugReport
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import coil3.compose.AsyncImage
import xyz.wingio.hellish.BuildConfig
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.component.BackButton
import xyz.wingio.hellish.ui.component.ThinDivider
import xyz.wingio.hellish.util.Constants

class AboutScreen: Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        val uriHandler = LocalUriHandler.current

        Scaffold(
            topBar = { TitleBar(scrollBehavior = scrollBehavior) },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { pv ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(pv)
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_hellish_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(170.dp)
                        .shadow(3.dp, CircleShape)
                        .background(
                            MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                            CircleShape
                        )
                        .padding(35.dp)
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.1.sp
                    )

                    Text(
                        text = Constants.VERSION_STRING.replace("%%APPNAME%%", "").trim(),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    )
                }

                ElevatedCard {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .clickable(onClickLabel = stringResource(R.string.action_open_github)) {
                                uriHandler.openUri(Constants.Developer.GITHUB_URL)
                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        AsyncImage(
                            model = "${Constants.Developer.GITHUB_URL}.png",
                            contentDescription = null,
                            modifier = Modifier
                                .size(55.dp)
                                .clip(CircleShape)
                        )

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.about_made_by),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = LocalContentColor.current.copy(alpha = 0.5f)
                            )

                            Text(
                                text = Constants.Developer.NAME,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.1.sp
                            )
                        }

                        Row {
                            IconButton(
                                onClick = { uriHandler.openUri(Constants.Developer.SPONSORS_URL) },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = stringResource(R.string.action_donate)
                                )
                            }

                            IconButton(
                                onClick = { uriHandler.openUri(Constants.Developer.WEBSITE_URL) },
                                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                                    contentDescription = stringResource(R.string.action_visit_creator_site)
                                )
                            }
                        }
                    }
                }

                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    LinkItem(icon = Icons.Outlined.Code, onClick = { uriHandler.openUri(BuildConfig.GIT_REPO_URL) }) {
                        Text(stringResource(R.string.about_source))
                    }

                    ThinDivider(
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                    )

                    LinkItem(icon = Icons.Outlined.BugReport, onClick = { uriHandler.openUri("${BuildConfig.GIT_REPO_URL}/issues/new") }) {
                        Text(stringResource(R.string.about_bug_report))
                    }

                    ThinDivider(
                        modifier = Modifier
                            .padding(horizontal = 18.dp)
                    )

                    LinkItem(icon = Icons.Outlined.Translate, onClick = { uriHandler.openUri(Constants.TRANSLATIONS_URL) }) {
                        Text(stringResource(R.string.about_translate))
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
        TopAppBar(
            title = { Text(stringResource(R.string.about_title)) },
            scrollBehavior = scrollBehavior,
            navigationIcon = { BackButton() }
        )
    }

    @Composable
    private fun LinkItem(
        icon: ImageVector,
        onClick: () -> Unit,
        label: @Composable BoxScope.() -> Unit,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null)

            Box(modifier = Modifier.weight(1f)) {
                label()
            }
        }
    }

}