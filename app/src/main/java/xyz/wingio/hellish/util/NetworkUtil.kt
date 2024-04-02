package xyz.wingio.hellish.util

import android.content.Context
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Url
import xyz.wingio.hellish.BuildConfig
import xyz.wingio.hellish.R
import xyz.wingio.hellish.rest.response.PageInfo

fun buildUserAgent(context: Context): String {
    return buildString {
        append(context.getString(R.string.app_name))
        append("/")
        append(BuildConfig.APPLICATION_ID)
        append(" - ")
        append(BuildConfig.VERSION_NAME)
        append(" (${BuildConfig.VERSION_CODE}/${BuildConfig.GIT_COMMIT})")
        append("; ${BuildConfig.GIT_REPO_URL}")
    }
}

/**
 * Extracts paging info from the links response header
 */
object LinkPageExtractor {

    private val LINK_REGEX = "<(.+?)>; rel=\"(next|prev)\"".toRegex()

    fun getPageInfo(response: HttpResponse): Pair<PageInfo?, PageInfo?> {
        val linkHeader = response.headers["links"] ?: return null to null
        val links = linkHeader.split(", ")
        var next: PageInfo? = null
        var prev: PageInfo? = null

        links.forEach { link ->
            val match = LINK_REGEX.matchEntire(link)
            val url = match?.groupValues?.get(1)
            val rel = match?.groupValues?.get(2)

            when(rel) {
                "next" -> next = extractFromParams(url)
                "prev" -> prev = extractFromParams(url)
            }
        }

        return next to prev
    }

    /**
     * Extracts the necessary paging parameters from the [path]
     *
     * @param path Path returned inside the links header
     */
    private fun extractFromParams(path: String?): PageInfo? {
        if(path == null) return null
        val parsedUrl = Url("${BuildConfig.BASE_URL}$path")

        return PageInfo(
            after = parsedUrl.parameters["after"],
            before = parsedUrl.parameters["before"]
        )
    }

}