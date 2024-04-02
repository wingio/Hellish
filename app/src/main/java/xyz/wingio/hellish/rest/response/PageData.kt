package xyz.wingio.hellish.rest.response

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter

data class PageInfo(
    val after: String? = null,
    val before: String? = null,
    val limit: Int = 30
)

fun HttpRequestBuilder.page(pageInfo: PageInfo?) {
    if (pageInfo == null) parameter("limit", 30)
    pageInfo?.let { page ->
        page.after?.let { parameter("after", it) }
        page.before?.let { parameter("before", it) }
        parameter("limit", page.limit)
    }
}