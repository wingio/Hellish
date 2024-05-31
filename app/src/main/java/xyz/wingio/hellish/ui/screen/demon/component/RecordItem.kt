package xyz.wingio.hellish.ui.screen.demon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.wingio.hellish.R
import xyz.wingio.hellish.domain.model.ModelRecord
import java.text.DecimalFormat

@Composable
fun RecordItem(
    record: ModelRecord,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current
    val flag = remember {
        record.nationality?.getFlag(context)
    }

    ElevatedCard(
        onClick = { uriHandler.openUri(record.video!!) } ,
        modifier = modifier.semantics {
            onClick(context.getString(R.string.action_watch_record), null)
        }
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                flag?.let {
                    Image(
                        bitmap = flag,
                        contentDescription = record.nationality?.nation,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = record.player?.name ?: stringResource(R.string.name_unknown),
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 15.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = {
                        record.progress / 100f
                    },
                    drawStopIndicator = {},
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = DecimalFormat("#%").format(record.progress / 100f),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}