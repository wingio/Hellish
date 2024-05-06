package xyz.wingio.hellish.ui.screen.demon.component

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.TopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import xyz.wingio.hellish.ui.component.BackButton
import kotlin.math.abs

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DemonTopAppBar(
    title: @Composable () -> Unit,
    thumbnailUrl: String?,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = { BackButton() },
    actions: @Composable RowScope.() -> Unit = {}
) {
    val highlightColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
    val statusBarHeight = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val bottomRowHeight = TopAppBarDefaults.LargeAppBarExpandedHeight - TopAppBarDefaults.LargeAppBarCollapsedHeight

    val expandedHeightPx: Float
    val collapsedHeightPx: Float
    val heightOffset: Dp

    LocalDensity.current.run {
        expandedHeightPx = TopAppBarDefaults.LargeAppBarExpandedHeight.toPx()
        collapsedHeightPx = TopAppBarDefaults.LargeAppBarCollapsedHeight.toPx()
        heightOffset = scrollBehavior.state.heightOffset.toDp()
    }

    SideEffect {
        // Required to not have scrolling break
        if (scrollBehavior.state.heightOffsetLimit != collapsedHeightPx - expandedHeightPx) {
            scrollBehavior.state.heightOffsetLimit = collapsedHeightPx - expandedHeightPx
        }
    }

    Box(
        modifier = modifier
            .height(
                TopAppBarDefaults.LargeAppBarExpandedHeight + statusBarHeight + heightOffset
            )
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(1 - scrollBehavior.state.collapsedFraction) // Inverse of the collapsed fraction
        )

        // Adds a fade to the area behind the status bar to give the status icons some contrast
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(statusBarHeight)
                .background(
                    Brush.verticalGradient(
                        0f to MaterialTheme.colorScheme.surface,
                        0.5f to MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        1f to Color.Transparent
                    )
                )
                .alpha(1 - scrollBehavior.state.collapsedFraction) // Inverse of the collapsed fraction
        )

        // Fade into the background color instead of harsh border, also adds a small highlight based on thumbnail colors
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to highlightColor.copy(alpha = 0.2f),
                        0.33f to highlightColor.copy(alpha = 0.6f),
                        0.66f to MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
                        1f to MaterialTheme.colorScheme.surface
                    )
                )
                .alpha(1 - scrollBehavior.state.collapsedFraction) // Inverse of the collapsed fraction
        )

        // Set up support for resizing the top app bar when vertically dragging the bar itself.
        // Taken from Compose Material3 - Copyright 2021 The Android Open Source Project
        val appBarDragModifier = if (!scrollBehavior.isPinned) {
            Modifier.draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    scrollBehavior.state.heightOffset += delta
                },
                onDragStopped = { velocity ->
                    settleAppBar(
                        scrollBehavior.state,
                        velocity,
                        scrollBehavior.flingAnimationSpec,
                        scrollBehavior.snapAnimationSpec
                    )
                }
            )
        } else {
            Modifier
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceContainer.copy(alpha = scrollBehavior.state.collapsedFraction)) // Fade in background on scroll
                .then(appBarDragModifier)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                    .height(TopAppBarDefaults.LargeAppBarCollapsedHeight)
                    .fillMaxWidth()
            ) {
                navigationIcon()

                Box(
                    modifier = Modifier
                        .alpha(scrollBehavior.state.collapsedFraction)
                        .weight(1f)
                ) {
                    ProvideTextStyle(MaterialTheme.typography.titleLarge) {
                        title()
                    }
                }

                actions()
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(bottomRowHeight + heightOffset) // Offset height (collapse) based on scroll
                    .alpha(1 - scrollBehavior.state.collapsedFraction) // Fade out on scroll
            ) {
                ProvideTextStyle(
                    MaterialTheme.typography.headlineMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    title()
                }
            }
        }
    }
}

/**
 * Taken from Compose Material3
 *
 * Copyright 2021 The Android Open Source Project
 *
 * @see androidx.compose.material3.settleAppBar
 */
@OptIn(ExperimentalMaterial3Api::class)
private suspend fun settleAppBar(
    state: TopAppBarState,
    velocity: Float,
    flingAnimationSpec: DecayAnimationSpec<Float>?,
    snapAnimationSpec: AnimationSpec<Float>?
): Velocity {
    // Check if the app bar is completely collapsed/expanded. If so, no need to settle the app bar,
    // and just return Zero Velocity.
    // Note that we don't check for 0f due to float precision with the collapsedFraction
    // calculation.
    if (state.collapsedFraction < 0.01f || state.collapsedFraction == 1f) {
        return Velocity.Zero
    }
    var remainingVelocity = velocity
    // In case there is an initial velocity that was left after a previous user fling, animate to
    // continue the motion to expand or collapse the app bar.
    if (flingAnimationSpec != null && abs(velocity) > 1f) {
        var lastValue = 0f
        AnimationState(
            initialValue = 0f,
            initialVelocity = velocity,
        )
            .animateDecay(flingAnimationSpec) {
                val delta = value - lastValue
                val initialHeightOffset = state.heightOffset
                state.heightOffset = initialHeightOffset + delta
                val consumed = abs(initialHeightOffset - state.heightOffset)
                lastValue = value
                remainingVelocity = this.velocity
                // avoid rounding errors and stop if anything is unconsumed
                if (abs(delta - consumed) > 0.5f) this.cancelAnimation()
            }
    }
    // Snap if animation specs were provided.
    if (snapAnimationSpec != null) {
        if (state.heightOffset < 0 &&
            state.heightOffset > state.heightOffsetLimit
        ) {
            AnimationState(initialValue = state.heightOffset).animateTo(
                if (state.collapsedFraction < 0.5f) {
                    0f
                } else {
                    state.heightOffsetLimit
                },
                animationSpec = snapAnimationSpec
            ) { state.heightOffset = value }
        }
    }

    return Velocity(0f, remainingVelocity)
}