package xyz.wingio.hellish.ui.screen.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import xyz.wingio.hellish.domain.manager.AuthManager
import xyz.wingio.hellish.rest.PointercrateClient
import xyz.wingio.hellish.rest.ifSuccessful

class LoginViewModel(
    private val authManager: AuthManager,
    private val pointercrateClient: PointercrateClient
): ScreenModel {

    var username by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var loginSuccess by mutableStateOf(false)
        private set

    var loading by mutableStateOf(false)
        private set

    fun login() {
        loading = true
        screenModelScope.launch {
            pointercrateClient.login(username, password).ifSuccessful {
                authManager.setToken(it.token)
                authManager.setUser(it.data)
                authManager.setPassedOnboarding(true)
                loginSuccess = true
            }
            loading = false
        }
    }

    fun updateUsername(newUsername: String) {
        username = newUsername
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

}