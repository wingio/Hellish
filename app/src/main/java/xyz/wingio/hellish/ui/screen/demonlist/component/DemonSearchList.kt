package xyz.wingio.hellish.ui.screen.demonlist.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import xyz.wingio.hellish.ui.component.ThinDivider
import xyz.wingio.hellish.ui.component.search.searchListItems
import xyz.wingio.hellish.ui.screen.demonlist.viewmodel.DemonListViewModel

@Composable
fun DemonSearchList(
    viewModel: DemonListViewModel
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        val items = viewModel.recentSearchManager.getFrecentDemonQueries(viewModel.searchQuery)

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.suggestedDemon?.let { suggestion ->
                item("suggestion") {
                    DemonSearchListItem(
                        position = suggestion.position,
                        name = suggestion.name,
                        publisher = suggestion.publisher!!.name,
                        thumbnail = suggestion.thumbnail,
                        onClick = {
                            viewModel.search(suggestion.name, suggestion)
                        },
                        modifier = Modifier.animateItem()
                    )
                }

                item("suggestion divider") {
                    ThinDivider()
                }
            }

            searchListItems(
                items = items,
                onSearch = {
                    viewModel.search(it.query, it.data)
                },
                onRefine = { viewModel.searchQuery = it.query }
            )
        }
    }
}