package xyz.wingio.hellish.domain.manager

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import xyz.wingio.hellish.domain.manager.base.BasePreferenceManager
import xyz.wingio.hellish.domain.manager.base.stringPreference
import xyz.wingio.hellish.rest.dto.User

class AuthManager(
    context: Context,
    private val json: Json
): BasePreferenceManager(context.getSharedPreferences("auth", Context.MODE_PRIVATE)) {

    var authToken by stringPreference("token", "")
        private set

    private var userPref by stringPreference("user", "")

    val user: User
        get() = json.decodeFromString(userPref)

    val isLoggedIn: Boolean
        get() = authToken.isNotBlank()

    fun setToken(newToken: String) {
        authToken = newToken
    }

    fun setUser(newUser: User) {
        userPref = json.encodeToString(newUser)
    }

}