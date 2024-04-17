package xyz.wingio.hellish.rest.response

import androidx.paging.PagingSource
import androidx.paging.PagingState

class ApiPagingSource<D : Any>(
    private val request: suspend (PageInfo?, Int, String?) -> PagedResponse<D>,
    private val query: String? = null
): PagingSource<PageInfo, D>() {

    override fun getRefreshKey(state: PagingState<PageInfo, D>) =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }

    override suspend fun load(params: LoadParams<PageInfo>): LoadResult<PageInfo, D> {
        val page = params.key

        @Suppress("MoveVariableDeclarationIntoWhen", "RedundantSuppression")
        val response = request(page, page?.limit ?: 30, query)

        return when (response) {
            is PagedResponse.Success -> LoadResult.Page(response.data, nextKey = response.nextPage, prevKey = response.previousPage)
            is PagedResponse.Empty -> LoadResult.Page(listOf(), null, null)
            is PagedResponse.Error -> LoadResult.Error(Error(response.error))
            is PagedResponse.Failure -> LoadResult.Error(response.error)
        }
    }

}