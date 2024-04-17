package xyz.wingio.hellish.rest.response

fun <D> ApiResponse<D>.ifSuccessful(block: (D) -> Unit) {
    if (this is ApiResponse.Success) block(data)
}

fun <D, T> ApiResponse<D>.transform(block: (D) -> T): ApiResponse<T> {
    return when (this) {
        is ApiResponse.Success -> ApiResponse.Success(block(data))
        is ApiResponse.Empty -> ApiResponse.Empty()
        is ApiResponse.Error -> ApiResponse.Error(error)
        is ApiResponse.Failure -> ApiResponse.Failure(throwable, body)
    }
}

fun <D, T> PagedResponse<D>.transform(block: (List<D>) -> List<T>): PagedResponse<T> {
    return when (this) {
        is PagedResponse.Success -> PagedResponse.Success(block(data), nextPage, previousPage)
        is PagedResponse.Empty -> PagedResponse.Empty()
        is PagedResponse.Error -> PagedResponse.Error(error)
        is PagedResponse.Failure -> PagedResponse.Failure(error, body)
    }
}

inline fun <D> PagedResponse<D>.ifSuccessful(block: (List<D>) -> Unit) {
    if (this is PagedResponse.Success) block(data)
}