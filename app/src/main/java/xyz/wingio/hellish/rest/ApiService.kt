package xyz.wingio.hellish.rest

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import xyz.wingio.hellish.rest.dto.ApiError
import xyz.wingio.hellish.util.LinkPageExtractor

class ApiService(
    val ktorClient: HttpClient,
    val json: Json
) {

    /**
     * Ktor wrapper for safer handling of requests and responses
     *
     * @param route The route to make the request to
     * @param method The HTTP method to use for this request
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> request(
        route: Route,
        method: HttpMethod = HttpMethod.Get,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = withContext(Dispatchers.IO) {
        var body: String? = null

        val response = try {
            val response = ktorClient.request {
                url(route.url)
                this.method = method

                builder?.invoke(this) // Apply the users desired configuration
            }

            if (response.status.isSuccess()) {
                body = response.bodyAsText()

                when {
                    response.status == HttpStatusCode.NoContent -> ApiResponse.Empty<D>() // Nothing is returned
                    D::class == String::class -> ApiResponse.Success(body as D)
                    else -> ApiResponse.Success(json.decodeFromString<D>(body))
                }
            } else {
                body = response.bodyAsText()

                when {
                    response.status == HttpStatusCode.Gone -> ApiResponse.Empty()
                    else -> ApiResponse.Error(json.decodeFromString<ApiError>(body)) // Otherwise, attempt to deserialize into the error type
                }
            }
        } catch (e: Throwable) {
            ApiResponse.Failure(e, body)
        }

        response
    }

    suspend inline fun <D> paged(
        route: Route,
        method: HttpMethod = HttpMethod.Get,
        noinline request: (HttpRequestBuilder.() -> Unit)? = null
    ): PagedResponse<D> {
        return withContext(Dispatchers.IO) {
            var body: String? = null // Holds the raw body text, in case of any failures

            val response = try {
                val response = ktorClient.request {
                    url(route.url)
                    this.method = method

                    request?.invoke(this) // Apply the users desired configuration
                }

                if (response.status.isSuccess()) { // Status code in the 2XX range
                    body = response.bodyAsText() // Save for deserialization

                    when {
                        response.status == HttpStatusCode.NoContent -> PagedResponse.Empty<D>() // Nothing is returned

                        else -> { // Otherwise, attempt to deserialize into a list of type T
                            val (nextPage, previousPage) = LinkPageExtractor.getPageInfo(response)
                            PagedResponse.Success(json.decodeFromString<List<D>>(body), nextPage, previousPage)
                        }
                    }
                } else { // Server returned an error
                    body = response.bodyAsText() // Save for deserialization

                    when {
                        response.status == HttpStatusCode.Gone -> PagedResponse.Empty()
                        else -> PagedResponse.Error(json.decodeFromString<ApiError>(body)) // Otherwise, attempt to deserialize into the error type
                    }
                }
            } catch (e: Throwable) {
                PagedResponse.Failure(e, body)
            }

            return@withContext response
        }
    }


    /**
     * Makes a GET request to the specified [route]
     *
     * @param route The route to make the request to
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> get(
        route: Route,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = request(route, HttpMethod.Get, builder = builder)

    /**
     * Makes a POST request to the specified [route]
     *
     * @param route The route to make the request to
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> post(
        route: Route,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = request(route, HttpMethod.Post, builder = builder)

    /**
     * Makes a PUT request to the specified [route]
     *
     * @param route The route to make the request to
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> put(
        route: Route,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = request(route, HttpMethod.Put, builder = builder)

    /**
     * Makes a PATCH request to the specified [route]
     *
     * @param route The route to make the request to
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> patch(
        route: Route,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = request(route, HttpMethod.Patch, builder = builder)

    /**
     * Makes a DELETE request to the specified [route]
     *
     * @param route The route to make the request to
     * @param builder Used to make any necessary alterations to the request, such as adding a body
     */
    suspend inline fun <reified D> delete(
        route: Route,
        noinline builder: (HttpRequestBuilder.() -> Unit)? = null
    ): ApiResponse<D> = request(route, HttpMethod.Delete, builder = builder)

}