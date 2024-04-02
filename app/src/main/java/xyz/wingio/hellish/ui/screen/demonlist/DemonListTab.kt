package xyz.wingio.hellish.ui.screen.demonlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.icon.filled.Demon
import xyz.wingio.hellish.ui.screen.demonlist.viewmodel.DemonListViewModel
import xyz.wingio.hellish.util.TabOptions

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

        Scaffold(
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { pv ->
            Box(
                modifier = Modifier.padding(pv)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        top = statusBarHeight + SearchBarDefaults.InputFieldHeight + 24.dp,
                        start = 16.dp, end = 16.dp, bottom = 16.dp
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        demons.itemCount,
                        key = demons.itemKey(),
                        contentType = demons.itemContentType()
                    ) { i ->
                        demons[i]?.let {
                            Text(text = "${it.position} ${it.name}")
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(statusBarHeight + 12.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.surface.copy(alpha = 0.8f), Color.Transparent),
                                tileMode = TileMode.Decal
                            )
                        )
                )

                Search()
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Search() {
        var query by remember {
            mutableStateOf("")
        }
        var active by remember {
            mutableStateOf(false)
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = {},
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text(text = "Search...") },
                leadingIcon = { Icon(Icons.Outlined.Search, null) },
                shadowElevation = 4.5.dp
            ) {
            }
        }
    }

}