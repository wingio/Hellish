package xyz.wingio.hellish.util

import xyz.wingio.hellish.BuildConfig

object Constants {

    object Developer {
        const val SPONSORS_URL = "https://github.com/sponsors/wingio"
        const val GITHUB_URL = "https://github.com/wingio"
        const val WEBSITE_URL = "https://wingio.xyz"
        const val NAME = "Wing"
    }

    const val TRANSLATIONS_URL = "https://crowdin.com/project/hellish"

    val VERSION_STRING = buildString {
        append("%%APPNAME%%")
        append(" v${BuildConfig.VERSION_NAME}")

        if (BuildConfig.DEBUG) {
            append(" (${BuildConfig.GIT_COMMIT}")
            if (BuildConfig.GIT_LOCAL_CHANGES || BuildConfig.GIT_LOCAL_COMMITS) append("*")
            append(" on ${BuildConfig.GIT_BRANCH})")
        }
    }

}