package xyz.wingio.hellish.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.net.toUri
import xyz.wingio.hellish.R

enum class VideoProvider(
    @DrawableRes val iconRes: Int,
    @StringRes val nameRes: Int
) {
    YOUTUBE(R.drawable.youtube, R.string.provider_youtube),
    TWITCH(R.drawable.twitch, R.string.provider_twitch),
    EVERYPLAY(R.drawable.play, R.string.provider_everyplay),
    VIMEO(R.drawable.vimeo, R.string.provider_vimeo),
    BILIBILI(R.drawable.bilibili, R.string.provider_bilibili),
    MISC(R.drawable.play, R.string.provider_misc);

    companion object {

        fun fromUrl(url: String): VideoProvider {
            val uri = url.toUri()
            println(uri.host)

            return when (uri.host?.lowercase()) {
                "www.youtube.com" -> YOUTUBE
                "www.twitch.tv" -> TWITCH
                "everyplay.com" -> EVERYPLAY
                "vimeo.com" -> VIMEO
                "www.bilibili.com" -> BILIBILI
                else -> MISC
            }
        }

    }
}