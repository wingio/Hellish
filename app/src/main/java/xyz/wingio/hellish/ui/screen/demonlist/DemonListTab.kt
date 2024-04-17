package xyz.wingio.hellish.ui.screen.demonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.delay
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.SearchDemon
import xyz.wingio.hellish.ui.icon.filled.Demon
import xyz.wingio.hellish.ui.screen.demonlist.component.DemonListItem
import xyz.wingio.hellish.ui.screen.demonlist.component.DemonSearchList
import xyz.wingio.hellish.ui.screen.demonlist.viewmodel.DemonListViewModel
import xyz.wingio.hellish.ui.screen.settings.SettingsScreen
import xyz.wingio.hellish.util.TabOptions
import xyz.wingio.hellish.util.navigate
import xyz.wingio.hellish.util.rememberPullToRefreshState
import kotlin.time.Duration.Companion.seconds

class DemonListTab: Tab {

    override val options: TabOptions
        @Composable get() = TabOptions(
            name = R.string.tab_demon_list,
            icon = Icons.Filled.Demon
        )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: DemonListViewModel = getScreenModel()
        val demons = viewModel.demons.collectAsLazyPagingItems()
        val statusBarHeight = WindowInsets.systemBars.asPaddingValues(LocalDensity.current).calculateTopPadding()
        val pullToRefreshState = rememberPullToRefreshState(isRefreshing = true)

        LaunchedEffect(Unit) {
            pullToRefreshState.startRefresh()
        }

        LaunchedEffect(demons.loadState.refresh) {
            if (demons.loadState.refresh == LoadState.NotLoading(false)) pullToRefreshState.endRefresh()
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                demons.refresh()
            }
        }

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { pv ->
            Box(
                modifier = Modifier
                    .padding(pv)
                    .nestedScroll(pullToRefreshState.nestedScrollConnection)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = statusBarHeight + SearchBarDefaults.InputFieldHeight + 8.dp + 32.dp, // Account for search and status bar, with an additional 32dp
                        start = 16.dp, end = 16.dp, bottom = 16.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(
                        demons.itemCount,
                        key = demons.itemKey(),
                        contentType = demons.itemContentType()
                    ) { i ->
                        demons[i]?.let {
                            DemonListItem(demon = it)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                ) {
                    PullToRefreshContainer(
                        state = pullToRefreshState,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = statusBarHeight + SearchBarDefaults.InputFieldHeight + 8.dp)
                    )
                }

                // Adds a fade to the area behind the status bar to give the status icons some contrast
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(statusBarHeight + 8.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                                    Color.Transparent
                                ),
                                tileMode = TileMode.Decal
                            )
                        )
                )

                Search(viewModel)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Search(
        viewModel: DemonListViewModel
    ) {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(viewModel.searchQuery) {
            if (viewModel.searchQuery.isNotBlank()) {
                delay(0.3.seconds)
                viewModel.getSearchSuggestions()
            } else {
                viewModel.clearSearch()
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                query = viewModel.searchQuery,
                onQueryChange = { viewModel.searchQuery = it },
                onSearch = { query -> viewModel.search(query, null as SearchDemon?) },
                active = viewModel.searchActive,
                onActiveChange = { viewModel.searchActive = it },
                placeholder = { Text(text = stringResource(R.string.placeholder_search)) },
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                trailingIcon = {
                    if (!viewModel.searchActive) {
                        IconButton(onClick = { navigator.navigate(SettingsScreen()) }) {
                            Icon(imageVector = Icons.Outlined.Settings, contentDescription = stringResource(R.string.action_open_settings))
                        }
                    } else if (viewModel.searchQuery.isNotBlank()) {
                        IconButton(onClick = { viewModel.clearSearch() }) {
                            Icon(imageVector = Icons.Outlined.Clear, contentDescription = stringResource(R.string.action_clear))
                        }
                    }
                },
                shadowElevation = 3.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    DemonSearchList(viewModel = viewModel)
                }
            }
        }
    }

}