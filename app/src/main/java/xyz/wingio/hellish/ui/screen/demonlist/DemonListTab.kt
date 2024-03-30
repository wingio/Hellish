package xyz.wingio.hellish.ui.screen.demonlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.icon.filled.Demon
import xyz.wingio.hellish.ui.icon.outlined.Demon
import xyz.wingio.hellish.util.TabOptions
import xyz.wingio.hellish.util.thenIf

class DemonListTab: Tab {

    override val options: TabOptions
        @Composable get() = TabOptions(
            name = R.string.tab_demon_list,
            icon = Icons.Filled.Demon
        )

    @Composable
    override fun Content() {
        Scaffold(
            topBar = { Search() }
        ) {
            Column(
                modifier = Modifier.padding(it)
            ) {

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
                leadingIcon = { Icon(Icons.Outlined.Search, null) }
            ) {
            }
        }
    }

}