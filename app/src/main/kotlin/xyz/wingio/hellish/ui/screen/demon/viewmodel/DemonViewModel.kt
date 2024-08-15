package xyz.wingio.hellish.ui.screen.demon.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import xyz.wingio.hellish.domain.manager.PaletteManager
import xyz.wingio.hellish.domain.manager.PreferenceManager
import xyz.wingio.hellish.domain.model.ModelDemon
import xyz.wingio.hellish.domain.repository.PointercrateRepository
import xyz.wingio.hellish.rest.response.ifSuccessful

class DemonViewModel(
    private val pointercrateRepository: PointercrateRepository,
    val preferences: PreferenceManager,
    val paletteManager: PaletteManager,
    private val id: Int
): ScreenModel {

    var demon by mutableStateOf(null as ModelDemon?)

    var loading by mutableStateOf(false)

    init {
        loadDemon()
    }

    fun loadDemon() {
        screenModelScope.launch {
            loading = true
            pointercrateRepository.getDemon(id).ifSuccessful {
                demon = it
            }
            loading = false
        }
    }

}