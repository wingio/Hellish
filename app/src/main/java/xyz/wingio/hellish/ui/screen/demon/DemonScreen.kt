package xyz.wingio.hellish.ui.screen.demon

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.koin.core.parameter.parametersOf
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.model.VideoProvider
import xyz.wingio.hellish.ui.screen.demon.component.DemonTopAppBar
import xyz.wingio.hellish.ui.screen.demon.component.RecordItem
import xyz.wingio.hellish.ui.screen.demon.viewmodel.DemonViewModel
import java.text.DecimalFormat

class DemonScreen(
    private val id: Int
): Screen {

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    override fun Content() {
        val viewModel: DemonViewModel = getScreenModel { parametersOf(id) }
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

        val colorScheme = if (viewModel.demon != null) viewModel.paletteManager.getThemeForImage(
            url = viewModel.demon!!.thumbnail!!,
            isDark = viewModel.preferences.isDark
        ) else MaterialTheme.colorScheme

        MaterialTheme(
            colorScheme = colorScheme
        ) {
            Scaffold(
                contentWindowInsets = WindowInsets(0, 0, 0 ,0),
                topBar = { Toolbar(viewModel, scrollBehavior) },
                modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { pv ->
                PullToRefreshBox(
                    isRefreshing = viewModel.loading,
                    onRefresh = { viewModel.loadDemon() },
                    modifier = Modifier
                        .padding(pv)
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp, 16.dp, 16.dp, 16.dp + WindowInsets.systemBars.asPaddingValues().calculateBottomPadding()),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                         viewModel.demon?.let { demon ->
                             item("rank and score") {
                                 Row(
                                     verticalAlignment = Alignment.CenterVertically,
                                     horizontalArrangement = Arrangement.spacedBy(16.dp),
                                     modifier = Modifier
                                         .fillMaxWidth()
                                         .padding(vertical = 9.dp)
                                 ) {
                                     Box(
                                         contentAlignment = Alignment.Center,
                                         modifier = Modifier
                                             .size(70.dp)
                                             .shadow(1.dp, CircleShape)
                                             .background(
                                                 MaterialTheme.colorScheme.primary,
                                                 CircleShape
                                             )
                                     ) {
                                         Text(
                                             text = "#${demon.position}",
                                             style = MaterialTheme.typography.titleLarge,
                                             color = MaterialTheme.colorScheme.onPrimary,
                                             textAlign = TextAlign.Center,
                                             fontSize = when (demon.position) {
                                                 in 1..9 -> 23.sp
                                                 in 10..99 -> 21.sp
                                                 else -> 19.sp
                                             },
                                             fontWeight = FontWeight.Bold,
                                             letterSpacing = 1.1.sp
                                         )
                                     }

                                     Row(
                                         verticalAlignment = Alignment.CenterVertically,
                                         horizontalArrangement = Arrangement.SpaceEvenly,
                                         modifier = Modifier
                                             .fillMaxWidth()
                                             .shadow(1.dp, CircleShape)
                                             .background(
                                                 MaterialTheme.colorScheme.secondaryContainer,
                                                 CircleShape
                                             )
                                     ) {
                                         @Composable
                                         fun ScoreAtProgress(
                                             progress: Int
                                         ) {
                                             Column(
                                                 horizontalAlignment = Alignment.CenterHorizontally,
                                                 modifier = Modifier.padding(10.dp)
                                             ) {
                                                 Text(
                                                     text = stringResource(R.string.demon_score_label, "$progress%"),
                                                     style = MaterialTheme.typography.titleSmall,
                                                     color = MaterialTheme.colorScheme.onSecondaryContainer
                                                 )

                                                 Text(
                                                     text = DecimalFormat("#.##").format(demon.score(progress)),
                                                     style = MaterialTheme.typography.titleMedium,
                                                     color = MaterialTheme.colorScheme.onSecondaryContainer
                                                 )
                                             }
                                         }

                                         ScoreAtProgress(demon.requirement!!)

                                         VerticalDivider(
                                             color = MaterialTheme.colorScheme.outline,
                                             modifier = Modifier.height(40.dp)
                                         )

                                         ScoreAtProgress(100)
                                     }
                                 }
                             }

                             items(demon.records ?: emptyList()) { record ->
                                 RecordItem(
                                     record = record,
                                     modifier = Modifier.fillMaxWidth()
                                 )
                             }
                         }
                    }
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun Toolbar(
        viewModel: DemonViewModel,
        scrollBehavior: TopAppBarScrollBehavior
    ) {
        val uriHandler = LocalUriHandler.current

        DemonTopAppBar(
            title = {
                if (viewModel.loading && viewModel.demon == null) {
                    CircularProgressIndicator()
                } else {
                    Column {
                        Text(
                            text = viewModel.demon?.name ?: stringResource(R.string.demon_load_error),
                        )

                        if (viewModel.demon != null) {
                            Text(
                                text = stringResource(R.string.demon_authorship, viewModel.demon!!.publisher!!.name, viewModel.demon!!.verifier!!.name),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .height(20.dp * (1 - scrollBehavior.state.collapsedFraction))
                                    .basicMarquee()
                            )
                        }
                    }
                }
            },
            actions = {
                viewModel.demon?.video?.let { videoUrl ->
                    IconButton(onClick = { uriHandler.openUri(videoUrl) }) {
                        Icon(
                            painter = painterResource(VideoProvider.fromUrl(videoUrl).iconRes),
                            contentDescription = stringResource(R.string.action_watch_verification)
                        )
                    }
                }
            },
            thumbnailUrl = viewModel.demon?.thumbnail,
            scrollBehavior = scrollBehavior
        )
    }

}