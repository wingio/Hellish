package xyz.wingio.hellish.ui.screen.demonlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.materialkolor.PaletteStyle
import org.koin.compose.koinInject
import xyz.wingio.hellish.domain.manager.PaletteManager
import xyz.wingio.hellish.domain.model.ModelDemon
import xyz.wingio.hellish.util.thenIf

@Composable
fun DemonListItem(
    demon: ModelDemon
) {
    val paletteManager: PaletteManager = koinInject()
    val thumbnailColors = paletteManager.getThemeForImage(demon.thumbnail!!, paletteStyle = PaletteStyle.Vibrant)

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (demon.position == 1) 0.dp else 6.dp
        ),
        modifier = Modifier
            .thenIf(demon.position == 1) {
                shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(12.dp),
                    spotColor = thumbnailColors.primary,
                    ambientColor = thumbnailColors.primary
                )
            }
    ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = demon.thumbnail,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    filterQuality = FilterQuality.High,
                    modifier = Modifier
                        .aspectRatio(2f)
                        .fillMaxWidth()
                )

                MaterialTheme(
                    colorScheme = thumbnailColors
                ) {
                    val surface = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)

                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        surface.copy(alpha = 0.8f),
                                        surface
                                    ),
                                    tileMode = TileMode.Repeated
                                )
                            )
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 64.dp, bottom = 16.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = demon.publisher?.name?.trim() ?: "Unknown",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            demon.position?.let {
                                DemonPlacementBadge(place = it)
                            }

                            Text(
                                text = demon.name.trim(),
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.5.sp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
    }
}