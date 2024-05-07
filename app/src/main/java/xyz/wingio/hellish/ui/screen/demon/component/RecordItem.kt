package xyz.wingio.hellish.ui.screen.demon.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import xyz.wingio.hellish.domain.model.ModelRecord

@Composable
fun RecordItem(
    record: ModelRecord,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val flag = remember {
        record.nationality?.getFlag(context)
    }

    ElevatedCard(
        modifier = modifier
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
                        contentDescription = null
                    )
                }

                Text(
                    text = record.player?.name ?: "Unknown",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 15.sp
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
                    text = "${record.progress}%",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}