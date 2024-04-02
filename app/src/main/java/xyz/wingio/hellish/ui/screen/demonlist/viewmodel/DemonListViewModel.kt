package xyz.wingio.hellish.ui.screen.demonlist.viewmodel

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import xyz.wingio.hellish.domain.repository.PointercrateRepository

class DemonListViewModel(
    pointercrateRepository: PointercrateRepository
): ScreenModel {

    val demons = Pager(
        PagingConfig(pageSize = 30)
    ) {
        DemonListPagingSource(pointercrateRepository)
    }.flow.cachedIn(screenModelScope)

}