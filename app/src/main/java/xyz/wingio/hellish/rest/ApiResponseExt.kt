package xyz.wingio.hellish.rest

fun <D> ApiResponse<D>.ifSuccessful(block: (D) -> Unit) {
    if (this is ApiResponse.Success) block(data)
}