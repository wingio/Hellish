package xyz.wingio.hellish.ui.component.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import xyz.wingio.hellish.domain.manager.SearchRecord
import xyz.wingio.hellish.ui.component.ThinDivider

fun <D> LazyListScope.searchListItems(
    items: List<SearchRecord<D>>,
    onSearch: (SearchRecord<D>) -> Unit,
    onRefine: (SearchRecord<D>) -> Unit
) {
    itemsIndexed(items) { i, record ->
        Column {
            SearchRecordItem(
                searchRecord = record,
                onClick = { onSearch(record) },
                onRefineClick = { onRefine(record) },
                modifier = Modifier.animateItem()
            )

            if (i != items.lastIndex) {
                ThinDivider()
            }
        }
    }
}