package xyz.wingio.hellish.util.coil

import coil3.annotation.ExperimentalCoilApi
import coil3.intercept.Interceptor
import coil3.request.ImageResult
import xyz.wingio.hellish.util.getHighResUrl
import xyz.wingio.hellish.util.url

/**
 * Automatically attempts to load the higher quality version
 * of any thumbnail and falls back to a lower quality version if it doesn't exist
 */
class HighResInterceptor : Interceptor {

    @OptIn(ExperimentalCoilApi::class)
    override suspend fun intercept(chain: Interceptor.Chain): ImageResult {
        val result = chain.request.newBuilder()
            .data(getHighResUrl(chain.url))
            .build()
            .let(chain::withRequest)
            .proceed()

        return if (result.image != null) {
            result
        } else {
            chain.proceed()
        }
    }

}