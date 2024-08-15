package xyz.wingio.hellish.util

import androidx.compose.ui.Modifier

fun Modifier.thenIf(predicate: Boolean, block: Modifier.() -> Modifier) =
    if (predicate) then(Modifier.block()) else this