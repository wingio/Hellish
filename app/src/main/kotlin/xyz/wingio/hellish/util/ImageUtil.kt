package xyz.wingio.hellish.util

const val YOUTUBE_LOW = "mqdefault"
const val YOUTUBE_HIGH = "maxresdefault"

fun getHighResUrl(url: String) = url.replace(YOUTUBE_LOW, YOUTUBE_HIGH)