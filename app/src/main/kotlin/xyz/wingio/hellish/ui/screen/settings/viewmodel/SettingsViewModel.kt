package xyz.wingio.hellish.ui.screen.settings.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel

class SettingsViewModel: ScreenModel {

    var confirmSignOutDialogOpened by mutableStateOf(false)
        private set

    fun openDialog() {
        confirmSignOutDialogOpened = true
    }

    fun closeDialog() {
        confirmSignOutDialogOpened = false
    }

}