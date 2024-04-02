package xyz.wingio.hellish.ui.screen.demonlist.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import xyz.wingio.hellish.domain.model.ModelDemon
import xyz.wingio.hellish.domain.repository.PointercrateRepository
import xyz.wingio.hellish.rest.response.PageInfo
import xyz.wingio.hellish.rest.response.PagedResponse

class DemonListPagingSource(
    private val pointercrateRepository: PointercrateRepository
): PagingSource<PageInfo, ModelDemon>() {

    override fun getRefreshKey(state: PagingState<PageInfo, ModelDemon>) =
        state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey
        }

    override suspend fun load(params: LoadParams<PageInfo>): LoadResult<PageInfo, ModelDemon> {
        val page = params.key

        @Suppress("MoveVariableDeclarationIntoWhen", "RedundantSuppression")
        val response = pointercrateRepository.getRankedDemons(page)

        return when (response) {
            is PagedResponse.Success -> LoadResult.Page(response.data, nextKey = response.nextPage, prevKey = response.previousPage)
            is PagedResponse.Empty -> LoadResult.Page(listOf(), null, null)
            is PagedResponse.Error -> LoadResult.Error(Error(response.error))
            is PagedResponse.Failure -> LoadResult.Error(response.error)
        }
    }

}