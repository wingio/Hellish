package xyz.wingio.hellish.util

import kotlin.math.pow
import kotlin.math.round

// Equivalent to Rust's f64.exp()
fun Double.exp() = Math.E.pow(this)

// Credit to fvasco
// https://discuss.kotlinlang.org/t/how-do-you-round-a-number-to-n-decimal-places/8843/2
fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}