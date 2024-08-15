package xyz.wingio.hellish.ui.component.search

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NorthWest
import androidx.compose.material.icons.outlined.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.manager.SearchDemon
import xyz.wingio.hellish.domain.manager.SearchRecord
import xyz.wingio.hellish.ui.screen.demonlist.component.DemonSearchListItem

@Composable
fun SearchRecordItem(
    searchRecord: SearchRecord<*>,
    onClick: () -> Unit,
    onRefineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (searchRecord.type) {
        SearchRecord.Type.DEMON -> {
            @Suppress("UNCHECKED_CAST")
            searchRecord as SearchRecord<SearchDemon>

            DemonSearchListItem(
                position = null,
                name = searchRecord.data!!.name,
                publisher = searchRecord.data!!.publisher,
                thumbnail = searchRecord.data!!.thumbnail,
                onClick = onClick,
                onRefineClick = onRefineClick,
                modifier = modifier
            )
        }

        SearchRecord.Type.QUERY -> {
            SearchListItem(
                text = { Text(searchRecord.query) },
                onClick = onClick,
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Restore, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(onClick = onRefineClick) {
                        Icon(imageVector = Icons.Outlined.NorthWest, contentDescription = stringResource(R.string.action_refine, searchRecord.query))
                    }
                },
                modifier = modifier
            )
        }

        else -> { }
    }
}