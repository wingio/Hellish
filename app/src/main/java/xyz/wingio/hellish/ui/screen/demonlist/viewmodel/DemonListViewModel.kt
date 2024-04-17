package xyz.wingio.hellish.ui.screen.demonlist.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import xyz.wingio.hellish.domain.manager.RecentSearchManager
import xyz.wingio.hellish.domain.manager.SearchDemon
import xyz.wingio.hellish.domain.model.ModelDemon
import xyz.wingio.hellish.domain.repository.PointercrateRepository
import xyz.wingio.hellish.rest.response.ifSuccessful

class DemonListViewModel(
    private val pointercrateRepository: PointercrateRepository,
    val recentSearchManager: RecentSearchManager
): ScreenModel {

    var suggestedDemon by mutableStateOf(null as ModelDemon?)
        private set

    var searchQuery by mutableStateOf("")
    var searchActive by mutableStateOf(false)

    val demons = Pager(
        PagingConfig(pageSize = 30)
    ) {
        DemonListPagingSource(pointercrateRepository)
    }.flow.cachedIn(screenModelScope)

    fun getSearchSuggestions() {
        screenModelScope.launch {
            pointercrateRepository.getDemons(limit = 1, query = searchQuery).ifSuccessful {
                suggestedDemon = it.firstOrNull()
            }
        }
    }

    fun clearSearch() {
        searchQuery = ""
        suggestedDemon = null
    }

    fun search(query: String, demon: ModelDemon? = null) = search(
        query,
        demon?.run {
            SearchDemon(name, publisher!!.name, id, thumbnail!!)
        }
    )

    fun search(query: String, demon: SearchDemon? = null) {
        searchQuery = query
        recentSearchManager.saveDemonSearch(query, demon)
        searchActive = false
    }

}