package xyz.wingio.hellish.util

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import coil3.intercept.Interceptor
import coil3.request.ImageRequest
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

val Interceptor.Chain.url: String
    get() = request.data.toString()

val ImageRequest.url: String
    get() = data.toString()

/**
 * Extracts paging info from the links response header
 */
object LinkPageExtractor {

    private val LINK_REGEX = "<(.+?)>; rel=(next|prev)".toRegex()

    fun getPageInfo(response: HttpResponse): Pair<PageInfo?, PageInfo?> {
        val linkHeader = response.headers["links"] ?: return null to null
        val links = linkHeader.split(",")
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

@Composable
@ExperimentalMaterial3Api
fun rememberPullToRefreshState(
    positionalThreshold: Dp = PullToRefreshDefaults.PositionalThreshold,
    isRefreshing: Boolean = false,
    enabled: () -> Boolean = { true },
): PullToRefreshState {
    val density = LocalDensity.current
    val positionalThresholdPx = with(density) { positionalThreshold.toPx() }
    return rememberSaveable(
        positionalThresholdPx, enabled,
        saver = PullToRefreshSaver(
            positionalThreshold = positionalThresholdPx,
            enabled = enabled,
        )
    ) {
        PullToRefreshState(positionalThresholdPx, isRefreshing, enabled)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun PullToRefreshSaver(
    positionalThreshold: Float,
    enabled: () -> Boolean,
) = Saver<PullToRefreshState, Boolean>(
    save = { it.isRefreshing },
    restore = { isRefreshing ->
        PullToRefreshState(positionalThreshold, isRefreshing, enabled)
    }
)