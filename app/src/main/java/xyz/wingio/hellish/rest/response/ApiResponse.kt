package xyz.wingio.hellish.rest.response

import xyz.wingio.hellish.rest.dto.response.ApiError

/**
 * Wrapper for safely handling http response data
 *
 * @param D The model to deserialize data into
 */
sealed interface ApiResponse<out D> {

    /**
     * Represents a request that successfully returned data
     *
     * @param data The data returned from the server
     */
    data class Success<D>(val data: D): ApiResponse<D>

    /**
     * Represents a response that has no body
     */
    class Empty<D>: ApiResponse<D>

    /**
     * Represent a request that received an error response from the server
     *
     * @param error The error returned from Discord
     */
    data class Error<D>(val error: ApiError): ApiResponse<D>

    /**
     * Represents an error that occurred on the client while processing the response, usually due to an inaccurate model or lack of an internet connection
     *
     * @param throwable The error that was thrown during response processing
     * @param body The raw response body sent by the server
     */
    data class Failure<D>(val throwable: Throwable?, val body: String?): ApiResponse<D>

}

/**
 * Wrapper for paged api responses that enables better error handling, includes information that can be used to paginate through a list
 *
 * @param T The api model for the response list item
 */
sealed interface PagedResponse<out T> {

    /**
     * [Data][data] was returned without any issues
     *
     * @param data The items in the current page
     * @param nextPage Information used to paginate forwards
     * @param previousPage Information used to paginate backwards
     */
    data class Success<T>(
        val data: List<T>,
        val nextPage: PageInfo?,
        val previousPage: PageInfo?
    ) : PagedResponse<T>

    /**
     * Represents a response that has no body
     */
    class Empty<T> : PagedResponse<T>

    /**
     * Represents an error returned by the server
     */
    data class Error<T>(val error: ApiError) : PagedResponse<T>

    /**
     * Represents an error on the client (Ex. deserialization problem or device is offline)
     */
    data class Failure<T>(val error: Throwable, val body: String?) : PagedResponse<T>
}