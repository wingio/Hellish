package xyz.wingio.hellish.ui.screen.demon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import org.koin.core.parameter.parametersOf
import xyz.wingio.hellish.ui.screen.demon.component.DemonTopAppBar
import xyz.wingio.hellish.ui.screen.demon.viewmodel.DemonViewModel

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
                    modifier = Modifier.padding(pv)
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                         // TODO
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
        DemonTopAppBar(
            title = {
                if (viewModel.loading && viewModel.demon == null) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = viewModel.demon?.name ?: "Error loading demon",
                    )
                }
            },
            thumbnailUrl = viewModel.demon?.thumbnail,
            scrollBehavior = scrollBehavior
        )
    }

}