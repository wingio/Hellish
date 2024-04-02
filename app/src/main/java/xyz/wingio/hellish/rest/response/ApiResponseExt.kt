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