package xyz.wingio.hellish.ui.screen.demonlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.NorthWest
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import xyz.wingio.hellish.R
import xyz.wingio.hellish.ui.component.search.SearchListItem

@Composable
fun DemonSearchListItem(
    position: Int?,
    name: String,
    publisher: String,
    thumbnail: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    onRefineClick: (() -> Unit)? = null
) {
    SearchListItem(
        text = {
            Text(
                text = buildAnnotatedString {
                    position?.let {
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                            append("$it. ")
                        }
                    }
                    append(name)
                }
            )
        },
        subtext = {
            Text("By $publisher")
        },
        onClick = onClick,
        leadingIcon = {
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .shadow(3.dp, RoundedCornerShape(16))
                    .clip(RoundedCornerShape(16))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
                    .size(36.dp)
            )
        },
        trailingIcon = if (onRefineClick == null) null else {{
            IconButton(onClick = onRefineClick) {
                Icon(imageVector = Icons.Outlined.NorthWest, contentDescription = stringResource(R.string.action_refine, name))
            }
        }},
        modifier = modifier
    )
}